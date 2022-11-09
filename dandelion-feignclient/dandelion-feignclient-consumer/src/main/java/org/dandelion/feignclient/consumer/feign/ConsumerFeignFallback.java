package org.dandelion.feignclient.consumer.feign;

import org.springframework.stereotype.Component;

/**
 * TODO fallback 异常处理
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/27 21:45
 */
@Component
public class ConsumerFeignFallback implements ConsumerFeign {
    @Override
    public String test(String s) {
        System.err.println(" ----------> ConsumerFeignFallback ");
        return "no";
    }
}
