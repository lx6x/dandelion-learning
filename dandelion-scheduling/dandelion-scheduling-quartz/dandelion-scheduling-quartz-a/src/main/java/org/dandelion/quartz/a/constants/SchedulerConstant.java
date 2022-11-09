package org.dandelion.quartz.a.constants;

/**
 * TODO 调度类型枚举
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 17:48
 */
public interface SchedulerConstant {

    class JOB {
        /**
         * kafka 发布消息
         */
        public static final String RELEASE = "RELEASE";

        /**
         * 暂无作用 测试使用
         */
        public static final String START_QUARTZ = "START_QUARTZ";
    }
}
