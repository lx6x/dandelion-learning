package org.dandelion.scheduling.rocketmq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liujunfei
 * @date 2023/5/16
 */
@Component
@RocketMQMessageListener(topic = "topic_b", consumerGroup = "consumer_group_b")
public class ConsumerBListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("b 开始消费：" + message);
    }
}
