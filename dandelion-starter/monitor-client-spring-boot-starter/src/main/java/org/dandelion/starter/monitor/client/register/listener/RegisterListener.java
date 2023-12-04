package org.dandelion.starter.monitor.client.register.listener;

import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.register.RegisterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RegisterListener implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(RegisterListener.class);

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationProperties applicationProperties;
    private final RegisterFactory registerFactory;


    public RegisterListener(RegisterFactory registerFactory, ApplicationProperties applicationProperties) {
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
        // 一直向server请求
        // 启动后执行注册方法
        boolean connect = applicationProperties.isConnect();
        if (connect) {
            taskScheduler.scheduleAtFixedRate(registerFactory::register, new Date(System.currentTimeMillis() + 5000), applicationProperties.getPeriod());
        }
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
