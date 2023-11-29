package org.dandelion.starter.monitor.client.config;

import org.dandelion.starter.monitor.client.listener.RegistrationApplicationListener;
import org.dandelion.starter.monitor.client.properties.ApplicationProperties;
import org.dandelion.starter.monitor.client.register.ApplicationFactory;
import org.dandelion.starter.monitor.client.register.ApplicationRegister;
import org.dandelion.starter.monitor.client.register.DefaultApplicationFactory;
import org.dandelion.starter.monitor.client.register.DefaultApplicationRegister;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
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
    public ApplicationRegister applicationRegister(ApplicationFactory applicationFactory) {
        return new DefaultApplicationRegister(applicationFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistrationApplicationListener registrationApplicationListener(ApplicationRegister applicationRegister) {
        return new RegistrationApplicationListener(applicationRegister);
    }
}
