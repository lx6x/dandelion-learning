package org.dandelion.doc.knife4j;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class DandelionDocKnife4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(DandelionDocKnife4jApplication.class, args);
    }

}
