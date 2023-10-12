// validating/CircularQueueException.java
package org.dandelion.onjava.validating;

/**
 * 16.2.2 Dbc + 单元测试
 *
 * @author lx6x
 * @date 2023/10/12
 */
public class CircularQueueException extends RuntimeException {
    public CircularQueueException(String why) {
        super(why);
    }
}