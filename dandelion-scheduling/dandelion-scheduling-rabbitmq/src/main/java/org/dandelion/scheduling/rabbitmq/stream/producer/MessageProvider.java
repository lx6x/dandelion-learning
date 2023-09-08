package org.dandelion.scheduling.rabbitmq.stream.producer;

/**
 * 消息生产者接口
 */
public interface MessageProvider {

    /**
     * 发送消息到 Direct 交换机
     */
    String sendToDirect();

    /**
     * 发送消息到 Fanout 交换机
     */
    String sendToFanout();

    /**
     * 发送消息到 Topic 交换机
     */
    String sendToTopic();

    /**
     * 发送延时消息
     */
    String sendToDirectDelay();
}
