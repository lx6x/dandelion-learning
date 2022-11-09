package org.dandelion.data.routing.source.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Data source switching processing
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 14:05
 */
public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);


    /**
     * Variables are maintained using ThreadLocal, which provides an independent copy of the variable for each thread that uses the variable,
     * So each thread can change its own copy independently without affecting the copies corresponding to other threads.
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * set data source type
     *
     * @param dsType .
     * @author L
     */
    public static void setDataSourceType(String dsType) {
        logger.info("----> set {} data source ",dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * Get the variables of the data source
     *
     * @return variables
     * @author L
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * clear the variables of the data source
     *
     * @author L
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
