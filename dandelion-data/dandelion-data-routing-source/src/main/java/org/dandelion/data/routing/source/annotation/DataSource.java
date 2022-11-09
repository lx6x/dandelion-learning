package org.dandelion.data.routing.source.annotation;

import org.dandelion.data.routing.source.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * TODO Custom data source switching annotations
 *      Priority: first method, then class, if the method overrides the data source type on the class, the method shall prevail, otherwise the class shall prevail
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 15:06
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    /**
     * toggle data source name
     *
     * @return DataSourceType
     */
    DataSourceType value() default DataSourceType.MASTER;
}
