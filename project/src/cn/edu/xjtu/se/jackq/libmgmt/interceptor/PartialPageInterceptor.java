package cn.edu.xjtu.se.jackq.libmgmt.interceptor;

import cn.edu.xjtu.se.jackq.libmgmt.annotation.PartialView;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class PartialPageInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Filter handler with partial view annotation
        if (handler instanceof HandlerMethod) {
            Method targetMethod = ((HandlerMethod) handler).getMethod();
            PartialView methodAnnotation = targetMethod.getDeclaredAnnotation(PartialView.class);
            // if a page is loaded via <jsp:include /> syntax, it will have an additional attribute labeled by container.
            String headerSign = (String) request.getAttribute("javax.servlet.include.request_uri");
            if (null != methodAnnotation) {
                // the target method has PartialView annotation, check the view source
                if (headerSign == null || headerSign.length() <= 0) {
                    // Return a error page
                    response.sendError(400);
                    return false;
                }
            } else {
                if (headerSign != null && headerSign.length() > 0) {
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
