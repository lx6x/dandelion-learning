package org.dandelion.flowable.flowable.config;

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
}
