package org.dandelion.feignclient.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 生产
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/27 21:43
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {


    @GetMapping("/test")
    public String test(@RequestParam String s) {
        System.err.println(" - - " + s);
        return "ok - " + s;
    }
}
