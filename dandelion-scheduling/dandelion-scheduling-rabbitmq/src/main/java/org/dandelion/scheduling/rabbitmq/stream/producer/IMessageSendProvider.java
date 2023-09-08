package org.dandelion.scheduling.rabbitmq.stream.producer;

import org.dandelion.scheduling.rabbitmq.stream.config.OutputMessageBinding;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

@EnableBinding(OutputMessageBinding.class)
public class IMessageSendProvider implements MessageProvider {

    @Resource
    private OutputMessageBinding outputMessageBinding;

    @Override
    public String sendToDirect() {
        // 发布消息到Direct交换机并设置routing key
        outputMessageBinding.output().send(MessageBuilder.withPayload("[info] This is a new message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "info").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[waring] This is a new waring message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "waring").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[error] This is new message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "error").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[test] This is a new message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "test").build());
        return "success";
    }

    @Override
    public String sendToFanout() {
        for (int i = 0; i < 3; i++) {
            // 发送消息到Fanout交换机
            outputMessageBinding.output().send(MessageBuilder.withPayload("This is a new message" + i).build());
        }
        return "success";
    }

    @Override
    public String sendToTopic() {
        // 发送消息到Topic交换机并设置routing key
        outputMessageBinding.output().send(MessageBuilder.withPayload("[aaa.bbb.ccc] This is a new message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "aaa.bbb.ccc").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[xxx.bbb.ccc] This is a new waring message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "xxx.bbb.ccc").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[null] This is a new message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "null").build());
        outputMessageBinding.output().send(MessageBuilder.withPayload("[xxx.yyy.zzz] This is a new test message.[" + System.currentTimeMillis() + "]").setHeader("routeTo", "xxx.yyy.zzz").build());
        return "success";
    }

    @Override
    public String sendToDirectDelay() {
        String message = "Hello, delay";
        Message<String> build = MessageBuilder.withPayload(message)
                .setHeader("x-delay", 6000)
                .build();
        outputMessageBinding.output().send(build);
        return "success";
    }
}
