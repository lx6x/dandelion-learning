package org.dandelion.scheduling.rocketmq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @date 2023/5/12
 */
@Component
@RocketMQMessageListener(topic = "topic_a",consumerGroup = "consumer_group_a")
public class ConsumerAListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("a 开始消费："+s);
    }
}
