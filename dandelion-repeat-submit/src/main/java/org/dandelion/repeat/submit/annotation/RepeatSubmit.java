package org.dandelion.repeat.submit.annotation;

import java.lang.annotation.*;

/**
 * TODO 重复提交注解验证
 *
 * @author L
 * @version 1.0
 * @date 2022/06/15 10:37
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    int interval() default 5000;

    /**
     * 提示消息
     */
    String message() default "一定之间内不允许多次访问，请稍候再试";
}
