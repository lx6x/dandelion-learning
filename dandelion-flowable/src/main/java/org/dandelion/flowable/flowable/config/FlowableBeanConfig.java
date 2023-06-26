package org.dandelion.flowable.flowable.config;

import org.dandelion.flowable.flowable.config.service.FlowableIdentityServiceImpl;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.ui.modeler.rest.app.ModelBpmnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Configuration
public class FlowableBeanConfig {
    @Bean
    public ModelBpmnResource modelBpmnResource() {
        return new ModelBpmnResource();
    }

  /*  @Bean
    public EngineConfigurationConfigurer<SpringIdmEngineConfiguration> idmEngineConfigurationConfigurer() {
        return idmEngineConfiguration -> idmEngineConfiguration.setIdmIdentityService(new FlowableIdentityServiceImpl(new IdmEngineConfiguration()));
    }*/

}
