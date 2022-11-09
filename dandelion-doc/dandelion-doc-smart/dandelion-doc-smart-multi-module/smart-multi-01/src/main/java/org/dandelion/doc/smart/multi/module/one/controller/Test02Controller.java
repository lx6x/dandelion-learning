package org.dandelion.doc.smart.multi.module.one.controller;

import org.dandelion.doc.smart.multi.module.tow.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 接口测试api - 02
 *
 * @author L
 * @version 1.0
 * @date 2021/11/2 14:50
 */
@RestController
public class Test02Controller {

    /**
     * 测试-1
     *
     * @author L
     */
    @GetMapping("/test01")
    public void test01() {

    }

    /**
     * 测试-2
     *
     * @param s 测试参数
     * @return string
     * @author L
     */
    @GetMapping("/test02")
    public String test02(String s) {
        return "ok";
    }

    /**
     * 测试-3
     *
     * @param test 实体测试
     * @return test
     * @author L
     */
    @PostMapping("/test03")
    public Test test03(@RequestBody Test test) {
        return new Test();
    }

    /**
     * 测试-4
     *
     * @author L
     */
    @PostMapping("/test04")
    public void test04() {

    }
}
