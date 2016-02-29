package cn.edu.xjtu.se.jackq.libmgmt.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;


public class StaticResourceAccessibilityInterceptor extends HandlerInterceptorAdapter {
    private String errorPageUrl = "/error/resource";

    public String getErrorPageUrl() {
        return errorPageUrl;
    }

    public void setErrorPageUrl(String errorPageUrl) {
        this.errorPageUrl = errorPageUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            // Spring Static Resource Type
            String referer = request.getHeader("referer");
            if (referer != null) {

                URL urlCurrent = new URL(request.getRequestURL().toString());
                URL urlPrevious = new URL(referer);
                if (urlCurrent.getHost().equalsIgnoreCase(urlPrevious.getHost())) {
                    return true;
                }
            }
            response.sendRedirect(request.getContextPath() + getErrorPageUrl());
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
