package org.dandelion.mybatis.flex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lx6x
 * @date 2023/12/13
 */
@SpringBootApplication
@MapperScan("org.dandelion.mybatis.flex.mapper")
public class MybatisFlexApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisFlexApplication.class, args);
    }
}
