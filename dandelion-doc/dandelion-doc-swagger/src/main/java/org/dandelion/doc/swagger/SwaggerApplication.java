package org.dandelion.doc.swagger;

import org.dandelion.commons.model.annotation.EnableGlobalException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/19 17:05
 */
@EnableGlobalException
@SpringBootApplication
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class,args);
    }
}
