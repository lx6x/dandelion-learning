package org.dandelion.starter.monitor.client.listener;

import org.dandelion.starter.monitor.client.register.ApplicationRegister;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 负责启动和停止注册任务
 *
 * @author lx6x
 * @date 2023/11/29
 */
public class RegistrationApplicationListener implements InitializingBean, DisposableBean {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationRegister applicationRegister;

    public RegistrationApplicationListener(ApplicationRegister applicationRegister) {
        this.taskScheduler = registrationTaskScheduler();
        this.applicationRegister=applicationRegister;
    }

    private static ThreadPoolTaskScheduler registrationTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("registrationTask");
        return taskScheduler;
    }

    @Override
    public void destroy() throws Exception {
        taskScheduler.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        taskScheduler.afterPropertiesSet();
    }

    @EventListener
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void onApplicationReady(ApplicationReadyEvent event) {
        boolean register = applicationRegister.register();
        System.out.println(register);
    }
}
