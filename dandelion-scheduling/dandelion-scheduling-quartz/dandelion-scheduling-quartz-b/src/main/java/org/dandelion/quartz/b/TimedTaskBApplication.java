package org.dandelion.quartz.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 11:46
 */
@EnableDiscoveryClient
@RefreshScope
@SpringBootApplication
public class TimedTaskBApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimedTaskBApplication.class, args);
    }
}
