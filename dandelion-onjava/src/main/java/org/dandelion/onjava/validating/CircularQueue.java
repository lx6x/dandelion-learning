package org.dandelion.onjava.validating;

import java.util.Arrays;

/**
 * 16.2.2 Dbc + 单元测试
 *
 * @author lx6x
 * @date 2023/10/12
 */
public class CircularQueue {

    private Object[] data;
    private int
            in = 0, //下一个可用的存储空间
            out = 0;//1 下一个可以获取的对象
    //是否已经回到了循环队列的开头？
    private boolean wrapped = false;

    public CircularQueue(int size) {
        data = new Object[size];
        // 构造后必须为真：
        assert invariant();
    }

    public boolean empty() {
        return !wrapped && in == out;
    }

    public boolean full() {
        return wrapped && in == out;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public void put(Object item) {
        precondition(item != null, "put() null item");
        precondition(!full(), "put() into full CircularQueue");
        assert invariant();
        data[in++] = item;
        if (in >= data.length) {
            in = 0;
            wrapped = true;
        }
        assert invariant();
    }

    public Object get() {
        precondition(!empty(),
                "get () from empty CircularQueue");
        assert invariant();
        Object returnVal = data[out];
        data[out] = null;
        out++;
        if (out >= data.length) {
            out = 0;
            wrapped = false;
        }
        assert postcondition(returnVal != null, "Null item in CircularQueue");
        assert invariant();
        return returnVal;
    }

    // 契约式设计的相关方法
    private static void precondition(boolean cond, String msg) {
        if (!cond) throw new CircularQueueException(msg);
    }

    private static boolean postcondition(boolean cond, String msg) {
        if (!cond) throw new CircularQueueException(msg);
        return true;
    }

    private boolean invariant() {
        // 保证在保存了对象的data 区城不会有空值：
        for (int i = out; i != in; i = (i + 1) % data.length)
            if (data[i] == null)
                throw new CircularQueueException(
                        "pull in CircularQueue");
        // 保证在保存了对象的data 区城之外只会有空值，
        if (full()) return true;
        for (int i = in; i != out; i = (i + 1) % data.length)
            if (data[i] != null)
                throw new CircularQueueException(
                        "non-null outside of CircularQueue range: " + dump());
        return true;
    }

    public String dump() {
        return " in = " + in +
                ", out = " + out +
                ", full() = " + full() +
                ", empty() = " + empty() +
                "， CircularQueue = " + Arrays.asList(data);
    }

}