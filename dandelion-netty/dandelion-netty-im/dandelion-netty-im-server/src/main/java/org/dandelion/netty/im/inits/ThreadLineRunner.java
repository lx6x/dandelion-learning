package org.dandelion.netty.im.inits;

import org.dandelion.netty.im.utils.RegisterToZk;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TODO register to Zk
 *
 * @author L
 * @version 1.0
 * @date 2022/5/13 18:08
 */
@Component
@Order(value = 2)
public class ThreadLineRunner implements CommandLineRunner {

    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor executor;

    /**
     * init register zk
     *
     * @param args start message
     * @author L
     * @date 2022/05/14
     */
    @Override
    public void run(String... args) throws Exception {
        executor.execute(new RegisterToZk());
    }
}
