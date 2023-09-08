package org.dandelion.scheduling.rabbitmq.stream.consumer;

import com.rabbitmq.client.Channel;
import org.dandelion.scheduling.rabbitmq.stream.config.InputMessageBinding;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@EnableBinding(InputMessageBinding.class)
public class MessageConsumer {


    /**
     * 监听输入流
     *
     * @param message 消息内容
     */
    @StreamListener(InputMessageBinding.INPUT)
    public void test(Message<String> message) throws IOException {
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        if (channel == null) {
            throw new RuntimeException("未获取到信道。");
        }
        if (deliveryTag == null) {
            throw new RuntimeException("未获取到消息标签信息");
        }
        // 回复ack，如已配置自动确认，则无需处理
        channel.basicAck(deliveryTag, false);
        String payload = message.getPayload();
        System.out.println(payload);
    }
}
