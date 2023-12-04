package org.dandelion.start.monitor.server.config;

import org.dandelion.start.monitor.server.register.InstancesRegister;
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
    public RegisterController registerController(InstancesRegister instancesRegister){
        return new RegisterController(instancesRegister);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstancesRegister instancesRegister(){
        return new InstancesRegister();
    }
}
