package org.dandelion.scheduling.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lx6x
 * @date 2023/8/31
 */
@EnableScheduling
@SpringBootApplication
public class SchedulingSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingSpringApplication.class, args);
    }
}
