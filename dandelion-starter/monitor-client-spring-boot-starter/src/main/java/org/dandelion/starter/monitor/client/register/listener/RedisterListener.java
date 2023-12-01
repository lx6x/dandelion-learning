package org.dandelion.starter.monitor.client.register.listener;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.register.RegisterFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 实际上可以直接用这个
 *
 * @author lx6x
 * @date 2023/11/30
 */
public class RedisterListener implements InitializingBean, DisposableBean {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationProperties applicationProperties;
    private final RegisterFactory registerFactory;


    public RedisterListener(RegisterFactory registerFactory, ApplicationProperties applicationProperties) {
        this.taskScheduler = registrationTaskScheduler();
        this.applicationProperties = applicationProperties;
        this.registerFactory = registerFactory;

    }

    private static ThreadPoolTaskScheduler registrationTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("registrationTask");
        return taskScheduler;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        // TODO 一直向server请求
        // 启动后执行注册方法
        taskScheduler.scheduleAtFixedRate(registerFactory::register, new Date(System.currentTimeMillis() + 5000), applicationProperties.getPeriod());

    }

    @Override
    public void destroy() throws Exception {
        taskScheduler.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        taskScheduler.afterPropertiesSet();
    }
}
