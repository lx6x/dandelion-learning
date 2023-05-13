package org.dandelion.scheduling.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class ConsumerMq {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_test");
        consumer.setNamesrvAddr("aaa:9876");
        consumer.subscribe("mq_test", "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (MessageExt messageExt : list) {
                String message = new String(messageExt.getBody());
                long bornTimestamp = messageExt.getBornTimestamp();
                System.err.println(message + "  " + bornTimestamp);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println("consumer statrt listener ...");
    }
}
