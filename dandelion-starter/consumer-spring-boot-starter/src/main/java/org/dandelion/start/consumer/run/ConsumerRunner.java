package org.dandelion.start.consumer.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.dandelion.start.producer.example.ProducerService;

/**
 * @author lx6x
 * @date 2023/11/28
 */
@Component
public class ConsumerRunner implements CommandLineRunner {


    private final ProducerService producerService;

    public ConsumerRunner(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void run(String... args) throws Exception {
        producerService.init();
    }
}
