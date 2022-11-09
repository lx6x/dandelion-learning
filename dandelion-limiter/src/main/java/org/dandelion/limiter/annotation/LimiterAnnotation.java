package org.dandelion.limiter.annotation;

import org.dandelion.limiter.limit.LimiterType;

import java.lang.annotation.*;

/**
 * TODO limiting annotation
 *
 * @author L
 * @version 1.0
 * @date 2022/6/7 10:57
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimiterAnnotation {

    /**
     * limiting key
     *
     * @return key
     */
    String key() default "limiter_annotation:";

    /**
     * limiting time,unit s
     *
     * @return time
     */
    int time() default 60;

    /**
     * limiting count
     *
     * @return count
     */
    int count() default 10;

    /**
     * limiting type
     *
     * @return LimitType
     */
    LimiterType limitType() default LimiterType.DEFAULT;

}
