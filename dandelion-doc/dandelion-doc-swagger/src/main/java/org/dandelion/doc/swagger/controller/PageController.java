package org.dandelion.doc.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO swagger主页
 *
 * @author L
 * @version 1.0
 * @date 2021/11/4 9:43
 */
@Controller
public class PageController {

    /**
     * 该接口可以写道拦截器中
     *
     * @return 页面跳转
     * @author L
     */
    @GetMapping("/")
    public String doc() {
        return "/doc.html";
    }
}
