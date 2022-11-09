package org.dandelion.commons.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 自定义权限验证
 *
 * @author L
 * @version 1.0
 * @date 2021/11/5 16:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {


    /**
     * 登录拦截
     *
     * @return true/false
     */
    boolean limit() default false;

    /**
     * 管理员权限
     *
     * @return true/false
     */
    boolean admin() default false;
}
