package org.dandelion.netty.biopool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO 线程池
 *
 * @author LJF
 * @version 1.0
 * @date 2022/04/21 17:28
 */
public class BioServerHandlerExecutePool {

    private ExecutorService executorService;

    public BioServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(5, maxPoolSize, 1201, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

}
