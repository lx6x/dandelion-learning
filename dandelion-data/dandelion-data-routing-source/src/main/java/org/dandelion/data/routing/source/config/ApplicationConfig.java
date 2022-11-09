package org.dandelion.data.routing.source.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.TimeZone;

/**
 * TODO EnableAspectJAutoProxy(exposeProxy = true): Indicates that the proxy object is exposed through the aop framework, and the Aop Context can access it
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 17:55
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("org.dandelion.data.routing.source.mapper")
public class ApplicationConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
