package org.dandelion.scheduling.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


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
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC = "some_topic";
    public static final String GROUP = "group";

    public void send(String s) {
        System.out.println("发送信息为：" + s);
//        kafkaTemplate.send
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, s);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("发送失败处理：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                System.out.println("发送成功处理：" + stringObjectSendResult.toString());
            }
        });
    }


}
