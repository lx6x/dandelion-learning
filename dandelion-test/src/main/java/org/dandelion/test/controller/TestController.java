package org.dandelion.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author LJF
 * @date 2020/12/3
 */
@Controller
public class TestController {

    @RequestMapping(value = "/index")
    public String index() {
        return "rtmp";
    }


    public static void main(String[] args) {
        String s = "Hello word";

        s = s.replace(" ", "2%");
        System.out.println(s);

    }
}
