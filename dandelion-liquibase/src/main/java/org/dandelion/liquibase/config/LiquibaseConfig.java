package org.dandelion.liquibase.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author lx6x
 * @date 2023/10/18
 */
@Configurable
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:liquibase/master.xml");
        springLiquibase.setShouldRun(true);
        return springLiquibase;
    }
}
