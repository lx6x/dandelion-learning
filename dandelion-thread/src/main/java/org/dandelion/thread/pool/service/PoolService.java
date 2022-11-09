package org.dandelion.thread.pool.service;

import org.dandelion.thread.pool.config.ThreadPoolStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 10:44
 */
@Service
public class PoolService {

    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 异步
     */
    @ThreadPoolStatus("t1--------------")
    @Async("asyncServiceExecutor")
    public void t1() {
        try {

            Thread.sleep(1000);
            System.out.println("UserController ti ------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    /**
     * 同步
     */
    @ThreadPoolStatus("t2--------------")
    public void t2() {
        Future<String> submit = threadPoolTaskExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Ok";
            }
        });
        try {
            String s = submit.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
