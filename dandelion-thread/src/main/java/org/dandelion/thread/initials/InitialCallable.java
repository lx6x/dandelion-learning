package org.dandelion.thread.initials;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * TODO 实现 Callable 接口创建线程
 *      可携带返回值
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 14:45
 */
public class InitialCallable implements Callable<Integer> {

    /**
     * @return string
     */
    @Override
    public Integer call() {
        int count = 0;
        for (int i = 0; i <= 100; i++) {
            count += i;
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        InitialCallable initialCallable = new InitialCallable();
        initialCallable.call();
        FutureTask<Integer> task = new FutureTask<>(initialCallable);
        Thread thread = new Thread(task);
        thread.start();
        Integer integer = task.get();
        System.out.println(integer);
    }
}
