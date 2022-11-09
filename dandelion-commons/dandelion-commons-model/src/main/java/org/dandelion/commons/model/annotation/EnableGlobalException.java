package org.dandelion.commons.model.annotation;

import org.dandelion.commons.model.exceptions.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
/**
 * TODO 启用全局异常
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 15:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({GlobalExceptionHandler.class})
public @interface EnableGlobalException {
}
