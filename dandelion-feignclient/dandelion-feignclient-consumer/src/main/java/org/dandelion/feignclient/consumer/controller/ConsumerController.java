package org.dandelion.feignclient.consumer.controller;

import org.dandelion.feignclient.consumer.feign.ConsumerFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO 消费
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/27 21:42
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private ConsumerFeign consumerFeign;

    @GetMapping("test")
    public void test(@RequestParam String s) {
        String test = consumerFeign.test(s);
        System.err.println(test);
    }
}
