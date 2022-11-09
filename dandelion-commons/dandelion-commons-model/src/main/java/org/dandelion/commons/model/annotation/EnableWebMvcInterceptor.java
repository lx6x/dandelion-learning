package org.dandelion.commons.model.annotation;

import org.dandelion.commons.model.intercepts.WebMvcConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO 启用 Web Mvc 拦截器
 *
 * @author L
 * @version 1.0
 * @date 2021/11/3 11:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({WebMvcConfig.class})
public @interface EnableWebMvcInterceptor {
}
