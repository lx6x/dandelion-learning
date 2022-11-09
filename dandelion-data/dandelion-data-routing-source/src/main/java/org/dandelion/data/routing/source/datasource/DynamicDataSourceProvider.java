package org.dandelion.data.routing.source.datasource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * TODO Load data
 *
 * @author L
 * @version 1.0
 * @date 2022/6/9 15:52
 */
public interface DynamicDataSourceProvider {


    /**
     * load data sources
     *
     * @return all data sources map
     * @author L
     */
    Map<String, DataSource> loadDataSources();
}
