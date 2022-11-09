package org.dandelion.feignclient.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO 消费 producer
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/27 21:44
 */
@FeignClient(name = "dandelion-feignclient-producer", fallback = ConsumerFeignFallback.class)
public interface ConsumerFeign {

    @GetMapping("/producer/test")
    String test(@RequestParam("s") String s);
}
