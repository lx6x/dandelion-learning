package org.dandelion.thread.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Java8 异步 CompletableFuture
 *
 * @author lx6x
 * @date 2023/9/4
 */
public class CompletableFutureExample {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    /**
     * 创建没有返回值的异步任务
     */
    public void runAsync() {

        System.out.println("2  当前线程: " + Thread.currentThread().getName());
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("1  当前线程: " + Thread.currentThread().getName());
            }
        });
        System.out.println("3  当前线程: " + Thread.currentThread().getName());
    }

    /**
     * 创建带有返回的异步任务
     */
    public CompletableFuture<String> supplyAsync() throws Exception {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4  当前线程: " + Thread.currentThread().getName());
            return "4";
        });

        System.out.println("获取返回值: " + supplyAsync.get());
        return supplyAsync;
    }

    /**
     * 使用自定义线程池执行异步任务
     */
    public void runAsyncExecutor() {
        CompletableFuture.runAsync(() -> {
            System.out.println("5  当前线程: " + Thread.currentThread().getName());
        }, executorService);

    }

    /**
     * 获取上一任务节点数据作为新的任务执行参数
     * 同一线程
     * 有返回值
     */
    public void thenApply() throws Exception {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4  当前线程: " + Thread.currentThread().getName());
            return "4";
        }, executorService);
        CompletableFuture<String> thenApply = supplyAsync.thenApply((then) -> {
            System.out.println("7  当前线程: " + Thread.currentThread().getName());
            System.out.println("上一任务结果为: " + then);
            return "7";
        });
        System.out.println(thenApply.get());
    }

    /**
     * 获取上一任务节点数据作为新的任务执行参数
     * 不同线程
     * 有返回值
     */
    public void thenApplyAsync() throws Exception {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4  当前线程: " + Thread.currentThread().getName());
            return "4";
        }, executorService);
        CompletableFuture<String> thenApply = supplyAsync.thenApplyAsync((then) -> {
            System.out.println("8  当前线程: " + Thread.currentThread().getName());
            System.out.println("上一任务结果为: " + then);
            return "8";
        });
        System.out.println("获取返回值: " + thenApply.get());
    }

    /**
     * 获取上一任务节点数据作为新的任务执行参数
     * 同一线程
     * 没有有返回值
     * <p>
     * 其他同上
     * thenAcceptAsync
     */
    public void thenAccept() {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4  当前线程: " + Thread.currentThread().getName());
            return "4";
        }, executorService);
        supplyAsync.thenAccept((then) -> {
            System.out.println("9  当前线程: " + Thread.currentThread().getName());
            System.out.println("上一任务结果为: " + then);
        });
    }

    /**
     * 等待 上一任务节点执行完毕
     * 同一线程
     * 不接受参数 没有有返回值
     * <p>
     * 其他同上
     * thenAcceptAsync
     */
    public void thenRun() {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("4  当前线程: " + Thread.currentThread().getName());
            System.out.println("执行完成");
            return "4";
        }, executorService);
        supplyAsync.thenRun(() -> {
            System.out.println("10  当前线程: " + Thread.currentThread().getName());
        });
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureExample completableFutureExample = new CompletableFutureExample();

//        completableFutureExample.supplyAsync();
//        completableFutureExample.runAsync();
//        completableFutureExample.runAsyncExecutor();
//        completableFutureExample.thenApply();
//        completableFutureExample.thenApplyAsync();
//        completableFutureExample.thenAccept();
        completableFutureExample.thenRun();
    }
}
