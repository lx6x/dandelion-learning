package org.dandelion.netty.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * TODO start class
 *
 * @author L
 * @version 1.0
 * @date 2022/5/16 14:53
 */
@ComponentScan(basePackages = {"org.dandelion.netty.common.utils","org.dandelion.netty.im","org.dandelion.netty.common.factory"})
@SpringBootApplication
public class NettyImRoutingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyImRoutingApplication.class, args);
    }
}
