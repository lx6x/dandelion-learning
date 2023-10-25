package com.dandelion.security.boot3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx6x
 * @date 2023/10/24
 */
@RestController
public class DemoController {

    @GetMapping("test")
    public String test(){
        return "ok";
    }
}
