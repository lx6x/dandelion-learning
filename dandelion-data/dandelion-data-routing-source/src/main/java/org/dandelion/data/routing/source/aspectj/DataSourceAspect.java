package org.dandelion.data.routing.source.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dandelion.data.routing.source.annotation.DataSource;
import org.dandelion.data.routing.source.datasource.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

import static org.dandelion.data.routing.source.utils.StringUtils.isNotNull;

/**
 * TODO Multiple data source processing
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 15:09
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * Pointcut: Specifies which methods need to be executed 'APO'
     * annotation: Used to match the currently executing method with the specified annotation
     * within(): Used to match all methods that hold the specified annotation type
     */
    @Pointcut("@annotation(org.dandelion.data.routing.source.annotation.DataSource) ||" +
        "@within(org.dandelion.data.routing.source.annotation.DataSource)")
    public void dsPointcut() {

    }


    /**
     * Dynamically switch data sources through apo
     *
     * @param point Get information about the proxy class and the proxy class
     * @return object
     */
    @Around("dsPointcut()")
    public Object around(ProceedingJoinPoint point) {
        DataSource dataSource = getDataSource(point);
        if (isNotNull(dataSource)) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * Get the data source that needs to be switched
     *
     * @param point Get information about the proxy class and the proxy class
     * @return DataSource
     */
    public DataSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        logger.info("----> method {} switch data source", method.getName());
        DataSource dataSource = AnnotationUtils.findAnnotation(method, DataSource.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        // 这行不知道是干嘛的 草 ...
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }


}
