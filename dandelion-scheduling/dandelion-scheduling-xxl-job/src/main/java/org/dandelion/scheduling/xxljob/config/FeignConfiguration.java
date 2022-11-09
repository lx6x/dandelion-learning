package org.dandelion.scheduling.xxljob.config;

import feign.RequestInterceptor;
import org.dandelion.commons.model.constants.RedisConstant;
import org.dandelion.commons.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * TODO FeignConfiguration
 *
 * @author L
 * @version 1.0
 * @date 2021/10/15 16:04
 */
@Configuration
public class FeignConfiguration {

    @Autowired
    private RedisUtils redisUtils;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != requestAttributes) {
                HttpServletRequest request = requestAttributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);
                    template.header(name, value);
                }
                String xxlJobLoginIdentity = RedisConstant.XxlJob.XXL_JOB_LOGIN_IDENTITY;
                String cookie = String.valueOf(redisUtils.get(RedisConstant.XxlJob.XXL_JOB_LOGIN_IDENTITY));
                template.header("Cookie", xxlJobLoginIdentity + "=" + cookie);
            }
        };
    }
}
