package org.dandelion.thread.initials;

/**
 * TODO 使用 Runnable 接口创建线程
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 14:40
 */
public class InitialRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello Runnable");
    }

    public static void main(String[] args) {
        System.out.println("---------------- 实现 Runnable 接口 ----------------");
        InitialRunnable initialRunnable=new InitialRunnable();
        System.out.println("---------------- 创建线程 ----------------");
        Thread thread=new Thread(initialRunnable);
        System.out.println("---------------- 启动线程 ----------------");
        thread.start();


    }
}
