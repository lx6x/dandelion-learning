package org.dandelion.thread.initials;

/**
 * TODO 继承 Thread 类创建线程
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 14:22
 */
public class InitialThread extends Thread {

    /**
     * 重写 run() 方法
     * run() 方法的方法体就是线程的执行体
     */
    @Override
    public void run() {
        System.out.println("Hello Thread");
    }

    public static void main(String[] args) {
        System.out.println("---------------- 创建线程 ----------------");
        InitialThread initialThread=new InitialThread();
        System.out.println("---------------- 启动线程 ----------------");
        initialThread.start();
    }
}

































