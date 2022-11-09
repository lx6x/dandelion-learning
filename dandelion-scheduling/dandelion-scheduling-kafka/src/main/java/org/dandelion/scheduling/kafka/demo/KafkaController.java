package org.dandelion.scheduling.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("send")
    public void send(@RequestBody String s) {
        kafkaProducer.send(s);
    }
}
