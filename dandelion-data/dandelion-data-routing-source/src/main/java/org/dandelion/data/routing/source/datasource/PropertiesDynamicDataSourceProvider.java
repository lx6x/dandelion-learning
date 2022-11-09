package org.dandelion.data.routing.source.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.dandelion.data.routing.source.enums.DataSourceType;
import org.dandelion.data.routing.source.properties.DruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/9 15:56
 */
@Configuration
public class PropertiesDynamicDataSourceProvider implements DynamicDataSourceProvider {


    @Value("${spring.datasource.ds.master.url}")
    private String masterUrl;
    @Value("${spring.datasource.ds.master.username}")
    private String masterUserName;
    @Value("${spring.datasource.ds.master.password}")
    private String masterPassword;


    @Value("${spring.datasource.ds.slave.url}")
    private String slaveUrl;
    @Value("${spring.datasource.ds.slave.username}")
    private String slaveUserName;
    @Value("${spring.datasource.ds.slave.password}")
    private String slavePassword;

    @Autowired
    private DruidProperties druidProperties;

    @Override
    public Map<String, DataSource> loadDataSources() {
        DruidDataSource masterDataSource = DruidDataSourceBuilder.create().build();
        masterDataSource.setUrl(masterUrl);
        masterDataSource.setUsername(masterUserName);
        masterDataSource.setPassword(masterPassword);
        DruidDataSource master = druidProperties.dataSource(masterDataSource);


        DruidDataSource slaveDataSource = DruidDataSourceBuilder.create().build();
        slaveDataSource.setUrl(slaveUrl);
        slaveDataSource.setUsername(slaveUserName);
        slaveDataSource.setPassword(slavePassword);
        DruidDataSource slave = druidProperties.dataSource(slaveDataSource);

        Map<String, DataSource> ds = new HashMap<>(16);
        ds.put(DataSourceType.MASTER.name(),master);
        ds.put(DataSourceType.SLAVE.name(), slave);

        return ds;
    }
}
