package org.dandelion.thread.interuption;

/**
 * TODO interrupt()
 *      方法验证：
 *          测试当前线程是否已经中断（该方法会清除线程中断状态）
 *          如果连续调用两次该方法，则第二次调用将会返回 false
 *          在第一次调用已清除了其中断状态之后，且第二次调用检验完中断状态之前，可以对当前线程再次中断
 *
 * @author L
 * @version 1.0
 * @date 2021/12/16 17:29
 */
public class InterruptedTest {

    public static void main(String[] args) {
        // 判断是否被中断
        System.out.println("1 Cleared or not： " + Thread.interrupted());
        Thread.currentThread().interrupt();
        // 判断是否被中断
        System.out.println("2 Cleared or not： " + Thread.interrupted());
        // 判断是否被中断
        System.out.println("3 Cleared or not： " + Thread.interrupted());
    }
}
