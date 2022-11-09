package org.dandelion.scheduling.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * TODO 消费者
 *
 * @author L
 * @version 1.0
 * @date 2021/9/15 14:33
 */
@Component
public class KafkaConsumer {


    /**
     * TODO 官方给出示例
     *      监听
     *
     * @param content 消费的值
     * @author L
     */
    @KafkaListener(topics = KafkaProducer.TOPIC)
    public void processMessage(String content) {
        System.out.println("消费-1：" + content);
    }

    @KafkaListener(topics = KafkaProducer.TOPIC, groupId = KafkaProducer.GROUP)
    public void consumer1(ConsumerRecord<?, ?> record) {
        System.out.println("消费-2" + record.topic() + " - " + record.partition() + " - " + record.value());
    }
}
