package org.dandelion.starter.monitor.client;

import org.dandelion.starter.monitor.client.config.ClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liujunfei
 * @date 2023/11/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ClientAutoConfiguration.class)
public @interface EnableCollectionClient {
}
