package org.dandelion.scheduling.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 测试
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 14:45
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("send/{s}")
    public void send(@PathVariable("s") String s) {
        kafkaProducer.send(s);
    }
}
