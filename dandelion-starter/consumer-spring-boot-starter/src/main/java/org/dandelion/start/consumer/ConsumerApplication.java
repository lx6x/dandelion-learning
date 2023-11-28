package org.dandelion.start.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lx6x
 * @date 2023/11/28
 */
@SpringBootApplication(scanBasePackages = {"org.dandelion.start"})
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

}
