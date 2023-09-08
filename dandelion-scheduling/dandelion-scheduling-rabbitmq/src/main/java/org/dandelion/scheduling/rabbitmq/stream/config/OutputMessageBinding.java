package org.dandelion.scheduling.rabbitmq.stream.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputMessageBinding {

    /**
     * 要使用的通道名称
     */
    String OUTPUT = "output";

    @Output(OUTPUT)
    MessageChannel output();

}
