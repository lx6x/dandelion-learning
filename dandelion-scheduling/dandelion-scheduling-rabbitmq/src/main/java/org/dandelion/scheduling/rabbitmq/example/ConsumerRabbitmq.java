package org.dandelion.scheduling.rabbitmq.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq消费者
 *
 * @author L
 * @version 1.0
 * @date 2021/11/16 11:24
 */
public class ConsumerRabbitmq {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("bbb");
        connectionFactory.setPort(10019);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        System.out.println("========== 连接工厂创建成功");

        // 创建连接
        Connection connection = connectionFactory.newConnection();
        System.out.println("========== 连接创建成功");

        // 创建信道
        Channel channel = connection.createChannel();
        System.out.println("========== 信道创建成功");

        String queueName = "dandelion.rabbitmq.test.queue";
        // s-队列名称
        channel.basicConsume(queueName, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("消费了：" + new String(body));
                // 消息消费确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        // 休眠两秒，不然还未消费连接就执行关闭了
        Thread.sleep(2000);
        System.out.println(" ========== 消费成功");

        // 关闭连接
        connection.close();
        connectionFactory.clone();
        System.out.println("========== 连接关闭成功");
    }
}
