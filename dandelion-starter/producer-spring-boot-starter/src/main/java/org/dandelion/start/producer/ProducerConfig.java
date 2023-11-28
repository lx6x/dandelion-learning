package org.dandelion.start.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.dandelion.start.producer.example.ProducerService;


/**
 * @author lx6x
 * @date 2023/11/28
 */
@Configuration
public class ProducerConfig {

    @Bean
    @ConditionalOnBean
    public ProducerService producerService() {
        return new ProducerService();
    }
}
