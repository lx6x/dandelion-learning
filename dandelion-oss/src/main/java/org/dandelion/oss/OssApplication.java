package org.dandelion.oss;

import org.dandelion.commons.model.annotation.EnableWebMvcInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/7 10:28
 */
@RefreshScope
@EnableDiscoveryClient
@EnableWebMvcInterceptor
@SpringBootApplication
@MapperScan("org.dandelion.oss.dao")
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
