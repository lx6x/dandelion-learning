package org.dandelion.scheduling.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq 生产者
 *
 * @author L
 * @version 1.0
 * @date 2021/11/16 11:23
 */
public class ProducerRabbitmq {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.44.128");
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

        // 创建交换器 s：交换器名称 s1：交换机类型(direct/topic/fanout)
        String exchangeName = "dandelion.rabbitmq.test.exchange";
        channel.exchangeDeclare(exchangeName, "direct");
        System.out.println("========== 交换器创建成功");

        // 创建队列 s：队列名称
        // durable 为true时server重启队列不会消失 (是否持久化)
        // exclusive 队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
        // autoDelete 当没有任何消费者使用时，自动删除该队列
        String queueName = "dandelion.rabbitmq.test.queue";
        channel.queueDeclare(queueName, false, false, false, null);
        System.out.println("========== 队列创建成功");

        // 队列与交换器绑定
        // queue：队列名称
        // exchange：交换机名称
        // routingKey：队列跟交换机绑定的键值
        String routingKey = "rabbitmq";
        channel.queueBind(queueName, exchangeName, routingKey);
        System.out.println("========== 绑定成功");

        //发送消息
        String message = "Hello Rabbitmq";

        // 向server发布一条消息
        // 参数1：exchange名字，若为空则使用默认的exchange
        // 参数2：routing key
        // 参数3：其他的属性
        // 参数4：消息体
        // RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
        // 任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
        channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("========== 消息发送成功");

        connectionFactory.clone();
        connection.close();
    }
}
