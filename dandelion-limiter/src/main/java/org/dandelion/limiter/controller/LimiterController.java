package org.dandelion.limiter.controller;

import org.dandelion.limiter.annotation.LimiterAnnotation;
import org.dandelion.limiter.limit.LimiterType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/7 11:25
 */
@RestController
@RequestMapping("/limiter")
public class LimiterController {


    @GetMapping("/defaultLimiter")
    @LimiterAnnotation
    public String defaultLimit() {
        return "ok";
    }

    @GetMapping("/ipLimiter")
    @LimiterAnnotation(limitType = LimiterType.IP)
    public String ipLimiter() {
        return "ok";
    }
}
