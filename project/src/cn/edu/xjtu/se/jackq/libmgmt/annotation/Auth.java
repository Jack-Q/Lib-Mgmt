package cn.edu.xjtu.se.jackq.libmgmt.annotation;

import cn.edu.xjtu.se.jackq.libmgmt.entity.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String[] userNames() default {};

    UserRole[] userRoles() default {};

    String[] deniedUserNames() default {};

    UserRole[] deniedUserRoles() default {};

    boolean allowAnonymous() default false;
    RedirectPolicy redirectPolicy() default RedirectPolicy.TO_CURRENT_PAGE;


    enum RedirectPolicy{
        TO_CURRENT_PAGE,
        TO_USER_PROFILE,
        TO_INDEX
    }

}
