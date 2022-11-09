package org.dandelion.thread.interuption;

/**
 * TODO 重新中断
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 16:59
 */
public class InterruptTest extends Thread {

    @Override
    public void run() {
        // for 循环等待线程中断 isInterrupted()-不会修改线程状态
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread running ...");
            // 线程阻塞，如果线程收到中断信号将抛出异常
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted ...");
                // false
                System.out.println(this.isInterrupted());
                // 中不中断由自己决定，如果需要真的中断线程，则需要重新设置中断位
                // 如果不需要，则不调用
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("reInterrupt ... " + this.isInterrupted());
        System.out.println("Thread exiting ...");
    }

    public static void main(String[] args) throws Exception {
        InterruptTest interruptTest = new InterruptTest();
        System.out.println("Starting thread ...");
        // 启动新线程
        interruptTest.start();
        // 主线程休眠 3 秒
        Thread.sleep(3000);
        System.out.println("Asking thread to stop ...");
        // 设置线程中断
        interruptTest.interrupt();
        // 主线程休眠 3 秒
        Thread.sleep(3000);
        System.out.println("Stopping application ...");
    }
}









































