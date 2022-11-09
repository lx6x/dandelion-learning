package org.dandelion.limiter.limit;

/**
 * TODO limiting type
 *
 * @author L
 * @version 1.0
 * @date 2022/6/7 10:53
 */
public enum LimiterType {

    /**
     * 默认全局限流
     */
    DEFAULT,

    /**
     * ip 限流
     */
    IP;
}
