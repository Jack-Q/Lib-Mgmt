package cn.edu.xjtu.se.jackq.libmgmt.interceptor;


import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.aopalliance.aop.Advice;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Base64;


public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private String authPageUrl = "/user/login";
    private String userProfileUrl ="/user/index";
    private String indexPageUrl = "/home/index";

    public String getAuthPageUrl() {
        return authPageUrl;
    }

    public void setAuthPageUrl(String authPageUrl) {
        this.authPageUrl = authPageUrl;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getIndexPageUrl() {
        return indexPageUrl;
    }

    public void setIndexPageUrl(String indexPageUrl) {
        this.indexPageUrl = indexPageUrl;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method targetMethod = ((HandlerMethod) handler).getMethod();
            Auth methodAnnotation = targetMethod.getDeclaredAnnotation(Auth.class);
            Class targetController = targetMethod.getDeclaringClass();
            Auth controllerAnnotation = (Auth) targetController.getDeclaredAnnotation(Auth.class);

            if (methodAnnotation != null || controllerAnnotation != null) {
                System.out.println("=====================================================================");
                System.out.println("Interceptor: Auth Check: " + getRedirectUrl(request));
                System.out.println("=====================================================================");
                HttpSession session = request.getSession();
                SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
                return checkAuth(sessionUser, methodAnnotation != null ? methodAnnotation : controllerAnnotation, request, response);
            }
        }
        return super.preHandle(request, response, handler);
    }

    boolean checkAuth(SessionUser sessionUser, Auth authAnnotation, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // For Anonymous, return to handler directly
        if(authAnnotation.allowAnonymous()){
            return true;
        }

        // Redirect to Auth page
        if(sessionUser== null || !sessionUser.isAuthorized()){
            String redirectUri = getAuthPageUrl();
            switch (authAnnotation.redirectPolicy()){
                case TO_CURRENT_PAGE:
                    redirectUri += "?returnTo=" + encodeParameter(getRedirectUrl(request));
                    break;
                case TO_USER_PROFILE:
                    redirectUri += "?returnTo=" + encodeParameter(getUserProfileUrl());
                    break;
                case TO_INDEX:
                    redirectUri += "?returnTo=" + encodeParameter(getIndexPageUrl());
                    break;
            }

            response.sendRedirect(redirectUri);
            return false;
        }


        return true;
    }

    String encodeParameter(String rawUri){
        return Base64.getEncoder().encodeToString(rawUri.getBytes());
    }

    String getRedirectUrl(HttpServletRequest request){
        String url= request.getRequestURI();
        if(null != request.getQueryString() ){
            url += "?" + request.getQueryString();
        }
        return url;
    }
}
