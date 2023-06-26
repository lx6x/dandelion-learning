package org.dandelion.flowable.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Configuration
@MapperScan(
        value = "org.dandelion.flowable.system.mapper",
        sqlSessionFactoryRef = "sqlSessionFactory",
        sqlSessionTemplateRef = "sqlSessionTemplate"
)
public class MybatisPlusConfig {

    /*
     * Flowable-ui-modeler和MybatisPlus sqlSessionFactory modelerSqlSessionFactory 冲突问题
     * 解决: 在扫描mapper指定mybatis-plus初始化的sqlSessionFactory和 sqlSessionTemplate
     *
     * 参考
     * https://blog.csdn.net/xixingzhe2/article/details/128094974
     * https://www.mdaima.com/it/5520.html
     */
}
