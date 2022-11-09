package org.dandelion.repeat.submit.controller;

import org.dandelion.repeat.submit.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO repeat submit
 *
 * @author L
 * @version 1.0
 * @date 2022/06/16 11:11
 */
@RestController
public class RepeatSubmitController {

    @RepeatSubmit
    @GetMapping("submit")
    public String submit() {
        return "ok";
    }
}
