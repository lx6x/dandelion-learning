package org.dandelion.netty.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO im server application
 *
 * @author L
 * @version 1.0
 * @date 2022/5/13 17:01
 */
@ComponentScan(basePackages = {"org.dandelion.netty.common.config","org.dandelion.netty.im","org.dandelion.netty.common.factory"})
@SpringBootApplication
public class NettyImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyImServerApplication.class, args);
    }

}
