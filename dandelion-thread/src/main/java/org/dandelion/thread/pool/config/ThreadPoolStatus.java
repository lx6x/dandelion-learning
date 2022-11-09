package org.dandelion.thread.pool.config;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 10:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface ThreadPoolStatus {

    String value() default "";
}
