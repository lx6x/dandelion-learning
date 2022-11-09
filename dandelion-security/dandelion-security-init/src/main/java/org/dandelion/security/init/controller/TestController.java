package org.dandelion.security.init.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 测试
 *
 * @author L
 * @version 1.0
 * @date 2022/2/28 17:57
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "ok";
    }

    @RequestMapping("test1")
    public String test1() {
        return "登录成功返回";
    }

    @RequestMapping("test2")
    public String test2() {
        return "登录成功返回";
    }

}
