package org.dandelion.scheduling.rabbitmq.stream.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputMessageBinding {

    /**
     * 要使用的通道名称 跟配置文件中一致
     */
    String OUTPUT = "output";

    @Output(OUTPUT)
    MessageChannel output();

}
