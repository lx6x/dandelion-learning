package org.dandelion.starter.monitor.client.config;

import org.dandelion.starter.monitor.client.listener.PushListener;
import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.push.DefaultPushFactory;
import org.dandelion.starter.monitor.client.push.PushFactory;
import org.dandelion.starter.monitor.client.register.ApplicationFactory;
import org.dandelion.starter.monitor.client.register.DefaultApplicationFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lx6x
 * @date 2023/11/29
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class ClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationFactory applicationFactory(ApplicationProperties applicationProperties) {
        return new DefaultApplicationFactory(applicationProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PushFactory pushFactory(ApplicationFactory applicationFactory, ApplicationProperties applicationProperties) {
        return new DefaultPushFactory(applicationFactory.createApplication(), applicationProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PushListener pushListener(PushFactory pushFactory, ApplicationProperties applicationProperties) {
        return new PushListener(pushFactory, applicationProperties);
    }

}