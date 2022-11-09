package org.dandelion.scheduling.xxljob;

import org.dandelion.commons.model.annotation.EnableGlobalException;
import org.dandelion.commons.model.annotation.EnableWebMvcInterceptor;
import org.dandelion.commons.utils.annotation.EnableRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * TODO 启动类
 *
 * @author L
 * @version 1.0
 * @date 2021/10/13 17:45
 */
@EnableGlobalException
@EnableWebMvcInterceptor
@EnableRedis
@EnableFeignClients
@SpringBootApplication
public class XxlJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobApplication.class, args);
    }


}
