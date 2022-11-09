package org.dandelion.commons.mybatisgenerator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 15:44
 */
@MapperScan("org.dandelion.commons.mybatisgenerator.mapper")
@SpringBootApplication
public class MybatisGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorApplication.class,args);
    }
}
