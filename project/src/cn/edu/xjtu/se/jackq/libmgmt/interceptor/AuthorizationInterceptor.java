package cn.edu.xjtu.se.jackq.libmgmt.interceptor;


import cn.edu.xjtu.se.jackq.libmgmt.annotation.Auth;
import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;
import cn.edu.xjtu.se.jackq.libmgmt.session.SessionUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;


public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private String authPageUrl = "/user/login";
    private String userProfileUrl = "/user/index";
    private String indexPageUrl = "/home/index";
    private String authDeniedUrl = "/error/denied";

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

    public String getAuthDeniedUrl() {
        return authDeniedUrl;
    }

    public void setAuthDeniedUrl(String authDeniedUrl) {
        this.authDeniedUrl = authDeniedUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("Auth");
        if (sessionUser == null) {
            sessionUser = new SessionUser();
            session.setAttribute("Auth", sessionUser);
        }

        if (handler instanceof HandlerMethod) {
            Method targetMethod = ((HandlerMethod) handler).getMethod();
            Auth methodAnnotation = targetMethod.getDeclaredAnnotation(Auth.class);
            Class targetController = targetMethod.getDeclaringClass();
            Auth controllerAnnotation = (Auth) targetController.getDeclaredAnnotation(Auth.class);

            if (methodAnnotation != null || controllerAnnotation != null) {
                System.out.println("=====================================================================");
                System.out.println("Interceptor: Auth Check: " + getRedirectUrl(request));
                System.out.println("=====================================================================");

                return checkAuth(sessionUser, methodAnnotation != null ? methodAnnotation : controllerAnnotation, request, response);
            }
        }
        return super.preHandle(request, response, handler);
    }

    boolean checkAuth(SessionUser sessionUser, Auth authAnnotation, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // For Anonymous, return to handler directly
        if (authAnnotation.allowAnonymous()) {
            return true;
        }

        String contextPath = request.getContextPath();

        // If no user logged in, Redirect to Auth page
        if (sessionUser == null || !sessionUser.isAuthorized()) {
            String redirectUri = getAuthPageUrl();
            switch (authAnnotation.redirectPolicy()) {
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

            response.sendRedirect(contextPath + redirectUri);
            return false;
        }

        // If user logged in, check if requires specific roles or names
        UserRole[] userRoles = authAnnotation.userRoles();
        String[] userNames = authAnnotation.userNames();
        UserRole[] deniedUserRoles = authAnnotation.deniedUserRoles();
        String[] deniedUserNames = authAnnotation.deniedUserNames();

        // Name policy is priority
        if (deniedUserNames.length > 0 && isInUserNames(sessionUser, deniedUserNames)) {
            response.sendRedirect(contextPath + getAuthDeniedUrl());
            return false;
        }

        if (userNames.length > 0 && isInUserNames(sessionUser, userNames)) {
            return true;
        }

        // Role policy is secondary
        if (userRoles.length > 0 && isInUserRoles(sessionUser, userRoles)) {
            return true;
        }

        if (deniedUserRoles.length > 0 && isInUserRoles(sessionUser, deniedUserRoles)) {
            response.sendRedirect(contextPath + getAuthDeniedUrl());
            return false;
        }

        // Not in white-list
        if (userNames.length > 0 || userRoles.length > 0) {
            response.sendRedirect(contextPath + getAuthDeniedUrl());
            return false;
        }

        // No policy applied or black-list policy, allow access
        return true;
    }

    boolean isInUserNames(SessionUser sessionUser, String[] userNames) {
        return Arrays.binarySearch(userNames, sessionUser.getUserName()) >= 0;
    }

    boolean isInUserRoles(SessionUser sessionUser, UserRole[] userRoles) {
        for (UserRole role : sessionUser.getRoles()) {
            if (Arrays.binarySearch(userRoles, role) >= 0) {
                return true;
            }
        }
        return false;
    }

    String encodeParameter(String rawUri) {
        return Base64.getEncoder().encodeToString(rawUri.getBytes());
    }

    String getRedirectUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (null != request.getQueryString()) {
            url += "?" + request.getQueryString();
        }
        return url;
    }

}
