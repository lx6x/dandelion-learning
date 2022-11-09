package org.dandelion.data.routing.source.datasource;

import org.dandelion.data.routing.source.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO dynamic data source
 *      AbstractRoutingDataSource: 主要是继承这个实现
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 13:59
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final DynamicDataSource HUNGRY_MAN_SINGLETON = new DynamicDataSource();

    private DynamicDataSourceProvider dynamicDataSourceProvider;

    private DynamicDataSource(){

    }

    public static DynamicDataSource getHungryManSingleton() {
        return HUNGRY_MAN_SINGLETON;
    }


    public DynamicDataSource setDynamicDataSourceProvider(DynamicDataSourceProvider dynamicDataSourceProvider) {
        this.dynamicDataSourceProvider=dynamicDataSourceProvider;
        Map<Object, Object> targetDataSources = new HashMap<>(dynamicDataSourceProvider.loadDataSources());
        super.setDefaultTargetDataSource(dynamicDataSourceProvider.loadDataSources().get(DataSourceType.MASTER.name()));
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
        return this;
    }

    public DynamicDataSource setDefaultTargetDataSource(String dataSourceName){
        super.setDefaultTargetDataSource(dynamicDataSourceProvider.loadDataSources().get(dataSourceName));
        return this;
    }

    @Override
    public Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
