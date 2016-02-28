package cn.edu.xjtu.se.jackq.libmgmt.annotation;

import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate the accessibility of a specific controller or request handler
 * <p>
 * The authentication is processed in the
 * {@link cn.edu.xjtu.se.jackq.libmgmt.interceptor.AuthorizationInterceptor AuthorizationInterceptor }.
 *
 * @author Jack Q
 * @see cn.edu.xjtu.se.jackq.libmgmt.interceptor.AuthorizationInterceptor
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String[] userNames() default {};

    UserRole[] userRoles() default {};

    String[] deniedUserNames() default {};

    UserRole[] deniedUserRoles() default {};

    boolean allowAnonymous() default false;

    RedirectPolicy redirectPolicy() default RedirectPolicy.TO_CURRENT_PAGE;


    enum RedirectPolicy {
        TO_CURRENT_PAGE,
        TO_USER_PROFILE,
        TO_INDEX
    }

}
