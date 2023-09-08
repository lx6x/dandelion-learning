package org.dandelion.scheduling.rabbitmq.example.delay.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 *
 *
 * @author lx6x
 * @version 1.0
 * @date 2021/12/15 10:46
 */
public class TaskDelayedRabbitmq {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.14.136");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("rabbitmq");
        connectionFactory.setPassword("rabbitmq");
        System.out.println("========== 连接工厂创建成功");

        // 创建连接
        Connection connection = connectionFactory.newConnection();
        System.out.println("========== 连接创建成功");

        // 创建信道 渠道
        Channel channel = connection.createChannel();
        System.out.println("========== 信道创建成功");

        String exchangeName = "dandelion.rabbitmq.delayed.exchange";

        channel.exchangeDeclare(exchangeName, "direct",true);
        System.out.println("========== 交换器创建成功");


        Map<String, Object> arguments = new HashMap<>(16);
        arguments.put("x-message-ttl",10000);
        String queueName = "dandelion.rabbitmq.task.queue";
        channel.queueDeclare(queueName, true, false, false, arguments);
        System.out.println("========== 队列创建成功");

        String routingKey = "rabbitmq.delayed";
        channel.queueBind(queueName, exchangeName, routingKey);
        System.out.println("========== 绑定成功");

        for (int i = 0; i < 10; i++) {
            //发送消息
            String message = i + "-" + System.currentTimeMillis();
            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("========== 消息发送成功：" + message);
            Thread.sleep(10000);
        }

        connectionFactory.clone();
        connection.close();
    }
}
