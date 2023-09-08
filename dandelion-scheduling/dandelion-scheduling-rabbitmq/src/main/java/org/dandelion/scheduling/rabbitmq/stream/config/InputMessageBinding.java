package org.dandelion.scheduling.rabbitmq.stream.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputMessageBinding {

    /**
     * 要使用的通道名称
     */
    String INPUT = "input";

    @Input(INPUT)
    SubscribableChannel input();
}
