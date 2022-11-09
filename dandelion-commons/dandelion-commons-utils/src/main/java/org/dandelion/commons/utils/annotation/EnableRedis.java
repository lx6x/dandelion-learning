package org.dandelion.commons.utils.annotation;

import org.dandelion.commons.utils.redis.RedisConfig;
import org.dandelion.commons.utils.redis.RedisUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO 启用 redis
 *
 * @author L
 * @version 1.0
 * @date 2021/11/5 15:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RedisConfig.class, RedisUtils.class})
public @interface EnableRedis {
}
