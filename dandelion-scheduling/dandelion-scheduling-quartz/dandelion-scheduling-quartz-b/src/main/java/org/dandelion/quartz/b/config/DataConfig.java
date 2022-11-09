package org.dandelion.quartz.b.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/9/26 10:56
 */
@Configuration
public class DataConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary.configuration")
    public DruidDataSource firstDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder()
                .type(DruidDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.quartz")
    public DataSourceProperties quartzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(value = "quartzDataSource")
    @QuartzDataSource
    @ConfigurationProperties("spring.datasource.quartz.configuration")
    public DruidDataSource quartzDataSource() {
        return quartzDataSourceProperties().initializeDataSourceBuilder()
                .type(DruidDataSource.class).build();
    }

}
