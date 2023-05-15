package org.dandelion.netty.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO 线程池配置
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 10:00
 */
@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Bean
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        // 获取Java虚拟机的可用的处理器数，最佳线程个数，处理器数*2。根据实际情况调整
        int curSystemThreads = Runtime.getRuntime().availableProcessors() * 2;
        logger.info("可使用最大线程数：{}", curSystemThreads);
        //配置最大线程数
        executor.setMaxPoolSize(curSystemThreads);
        //配置队列大小
        executor.setQueueCapacity(99999);
        // 允许线程的空闲时间60秒：当超过了核心线程之外的线程,在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        // 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，
        // 该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
