package org.dandelion.scheduling.rabbitmq.example.rpc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.dandelion.scheduling.rabbitmq.example.RabbitProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rpc 远程调用 server 端
 * <p>客户端发送请求消息，服务端回复响应的消息。为了接收响应的消息，我们需要在请求消息中发送一个回调队列，可以使用默认的队列。
 * <p>replyTo: 通常用来设置一个回调队列，用于客户端获取响应信息。
 * <p>correlationId: 用来关联请求(request)和其调用RPC之后的回复(response)，保证请求与响应是对应的。
 *
 * @author lx6x
 * @version 1.0
 * @date 2021/12/02 23:26
 */
public class ClientRpcRabbitmq {

    // 队列名称
    public static final String QUEUE_NAME = "rpc.client";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitProperties.Constant.IP_ADDRESS);
        connectionFactory.setPort(RabbitProperties.Constant.PORT);
        connectionFactory.setUsername(RabbitProperties.Constant.USER_NAME);
        connectionFactory.setPassword(RabbitProperties.Constant.PASSWORD);

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        // 创建信道
        Channel channel = connection.createChannel();


    }

}
