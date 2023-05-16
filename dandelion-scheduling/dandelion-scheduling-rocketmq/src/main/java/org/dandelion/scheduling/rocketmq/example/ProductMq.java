package org.dandelion.scheduling.rocketmq.example;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProductMq {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("product_job_a");
        producer.setNamesrvAddr("aaa:9876");
        producer.start();
        Message msg = new Message();
        msg.setTopic("topic_a");
        msg.setBody("ok".getBytes());

        SendResult send = producer.send(msg);
        System.out.println(send);
        producer.shutdown();
    }
}
