package org.dandelion.feignclient.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/27 21:34
 */
@EnableFeignClients(basePackages = {"org.dandelion.feignclient.consumer.feign"})
@SpringBootApplication
public class FeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
