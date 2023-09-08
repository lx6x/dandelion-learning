package org.dandelion.scheduling.rabbitmq.stream.controller;

import org.dandelion.scheduling.rabbitmq.stream.producer.MessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description: 发送消息测试
 */
@RestController
public class TestController {

    @Resource
    private MessageProvider messageProvider;

    @GetMapping("/sendDirect")
    public String sendDirectMessage() {
        return messageProvider.sendToDirect();
    }

    @GetMapping("/sendFanout")
    public String sendFanoutMessage() {
        return messageProvider.sendToFanout();
    }

    @GetMapping("/sendTopic")
    public String sendTopicMessage() {
        return messageProvider.sendToTopic();
    }
}
