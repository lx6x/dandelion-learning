package org.dandelion.scheduling.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import java.util.ArrayList;
import java.util.List;

class User {

    String name;
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

public class ProductMq {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("producer_test");
        producer.setNamesrvAddr("aaa:9876");
        producer.start();
        Message msg = new Message();
        msg.setTopic("mq_test");

        SendResult send = producer.send(msg);
        System.out.println(send);
        producer.shutdown();
    }
}
