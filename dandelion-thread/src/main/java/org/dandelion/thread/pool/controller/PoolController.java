package org.dandelion.thread.pool.controller;

import org.dandelion.thread.pool.service.PoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 线程池使用测试
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 10:04
 */
@RestController
public class PoolController {

    private static final Logger logger = LoggerFactory.getLogger(PoolController.class);

    @Autowired
    private PoolService poolService;

    @GetMapping("/t1")
    public void t1() {
        for (int i = 0; i <= 100; i++) {
            poolService.t1();
        }
    }

    @GetMapping("/t2")
    public void t2() {
        for (int i = 0; i < 20; i++) {
            poolService.t2();
        }
    }
}
