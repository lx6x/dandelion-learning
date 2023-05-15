package org.dandelion.netty.common.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO 获得 ApplicationContext 中的所有 bean，可以直接获取 spring 配置文件中，所有有引用到的 bean 对象
 *
 * @author L
 * @version 1.0
 * @date 2022/5/13 17:24
 */
@Component
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 获取指定的 bean 实例
     *
     * @param c type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @author L
     */
    public static <T> T getBean(Class<T> c) {
        return context.getBean(c);
    }

    /**
     * 获取指定的 bean 实例
     *
     * @param name  the name of the bean to retrieve
     * @param clazz type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @author L
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context =applicationContext;
    }
}
