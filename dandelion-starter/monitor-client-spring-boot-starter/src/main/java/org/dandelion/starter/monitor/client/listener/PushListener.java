package org.dandelion.starter.monitor.client.listener;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.push.PushFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 实际上可以直接用这个
 *
 * @author liujunfei
 * @date 2023/11/30
 */
public class PushListener implements InitializingBean, DisposableBean {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationProperties applicationProperties;
    private final PushFactory pushFactory;


    public PushListener(PushFactory pushFactory, ApplicationProperties applicationProperties) {
        this.taskScheduler = registrationTaskScheduler();
        this.applicationProperties=applicationProperties;
        this.pushFactory = pushFactory;

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
        taskScheduler.scheduleAtFixedRate(pushFactory::register, new Date(System.currentTimeMillis() + 5000), applicationProperties.getPeriod());
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
