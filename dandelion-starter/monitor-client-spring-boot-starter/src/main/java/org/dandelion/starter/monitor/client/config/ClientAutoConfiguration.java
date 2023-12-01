package org.dandelion.starter.monitor.client.config;

import org.dandelion.starter.monitor.client.register.listener.RedisterListener;
import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.register.DefaultRegisterFactory;
import org.dandelion.starter.monitor.client.register.RegisterFactory;
import org.dandelion.starter.monitor.client.application.ServiceInfoFactory;
import org.dandelion.starter.monitor.client.application.DefaultServiceInfoFactory;
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
    public ServiceInfoFactory applicationFactory(ApplicationProperties applicationProperties) {
        return new DefaultServiceInfoFactory(applicationProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisterFactory pushFactory(ServiceInfoFactory serviceInfoFactory, ApplicationProperties applicationProperties) {
        return new DefaultRegisterFactory(serviceInfoFactory.createApplication(), applicationProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisterListener pushListener(RegisterFactory registerFactory, ApplicationProperties applicationProperties) {
        return new RedisterListener(registerFactory, applicationProperties);
    }

}