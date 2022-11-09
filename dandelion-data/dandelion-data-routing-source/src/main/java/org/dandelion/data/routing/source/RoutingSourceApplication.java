package org.dandelion.data.routing.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * TODO start class
 *      exclude = {DataSourceAutoConfiguration.class}: 避免动态配置的数据源后，启动时出现循环依赖
 *
 * @author L
 * @version 1.0
 * @date 2022/6/7 17:37
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RoutingSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoutingSourceApplication.class, args);
    }
}
