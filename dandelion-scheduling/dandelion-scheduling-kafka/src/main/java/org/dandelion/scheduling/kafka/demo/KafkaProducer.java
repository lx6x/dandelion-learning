package org.dandelion.scheduling.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * TODO 生产者
 *      spring for kafka the official documentation -> https://spring.io/projects/spring-kafka
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 14:22
 */
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "some_topic";
    public static final String GROUP = "myGroup";

    public void send(String s) {
        System.out.println("发送信息为：" + s);
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(TOPIC, s);
        send.whenComplete((result, ex) -> {
        });

    }


}
