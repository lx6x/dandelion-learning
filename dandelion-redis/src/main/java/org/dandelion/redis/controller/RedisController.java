package org.dandelion.redis.controller;

import jakarta.annotation.Resource;
import org.dandelion.redis.config.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        return (String) redisService.get(key);
    }

    @GetMapping("/set/{key}/{value}")
    public void set(@PathVariable("key") String key, @PathVariable("value") String value) {
        redisService.set(key, value);
    }
}
