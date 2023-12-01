package org.dandelion.start.monitor.server.config;

import org.dandelion.start.monitor.server.register.RegisterController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lx6x
 * @date 2023/12/1
 */
@Configuration(proxyBeanMethods = false)
public class ServerWebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RegisterController registerController(){
        return new RegisterController();
    }
}
