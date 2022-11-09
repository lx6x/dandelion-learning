package org.dandelion.commons.logs;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/1/24 11:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecordAnnotation {

    /**
     * 操作日志的文本模板
     * 是否必填：是
     */
    String success();

    /**
     * 操作日志失败的文本版本
     * 是否必填：否
     */
    String fail() default "";

    /**
     * 操作日志的执行人
     * 是否必填：否
     */
    String operator() default "";

    /**
     * 操作日志绑定的业务对象标识
     * 是否必填：是
     */
    String bizNo();

    /**
     * 操作日志的种类
     * 是否必填：否
     */
    String category() default "";

    /**
     * 扩展参数，记录操作日志的修改详情
     * 是否必填：否
     */
    String detail() default "";

    /**
     * 记录日志的条件
     * 是否必填：否
     */
    String condition() default "";
}
