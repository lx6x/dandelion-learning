package org.dandelion.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/10 10:30
 */
@SpringBootApplication
public class FlowableApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

}
