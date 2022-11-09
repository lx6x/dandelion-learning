package org.dandelion.thread.interuption;

/**
 * TODO isInterrupted()：测试线程是否已经中断，但线程的中断状态不受该方法的影响。
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 17:40
 */
public class IsInterruptedTest {

    public static void main(String[] args) {
        // 当前线程
        Thread thread = Thread.currentThread();
        // 判断是否被中断
        System.out.println("1 Cleared or not： " + thread.isInterrupted());
        thread.interrupt();
        // 判断是否被中断
        System.out.println("2 Cleared or not： " + thread.isInterrupted());
        // 判断是否被中断
        System.out.println("3 Cleared or not： " + thread.isInterrupted());
        try {
            Thread.sleep(2000);
            System.out.println("not interrupted ...");
        } catch (InterruptedException e) {
            System.out.println("Thread.sleep interrupted ...");
            // 判断是都被中断
            System.out.println("4 Cleared or not：" + thread.isInterrupted());
        }
        System.out.println("5 Cleared or not：" + thread.isInterrupted());

    }
}
