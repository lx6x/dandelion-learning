package org.dandelion.start.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lx6x
 * @date 2023/11/28
 */
@Configuration
public class ProducerConfig {

    @Bean
    @ConditionalOnMissingBean
    public ProducerService producerService() {
        return new ProducerService();
    }
}
