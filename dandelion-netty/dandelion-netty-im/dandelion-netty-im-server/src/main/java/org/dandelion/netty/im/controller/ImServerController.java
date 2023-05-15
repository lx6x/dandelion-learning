package org.dandelion.netty.im.controller;

import io.netty.channel.Channel;
import org.dandelion.netty.common.bean.ChatInfo;
import org.dandelion.netty.common.constant.MessageConstant;
import org.dandelion.netty.common.protocol.MessageProto;
import org.dandelion.netty.im.hadle.ChannelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 19:13
 */
@RestController
public class ImServerController {

    private static final Logger logger = LoggerFactory.getLogger(ImServerController.class);

    private final ChannelMap channelMap = ChannelMap.newInstance();

    @PostMapping("/pushMessage")
    public void pushMessage(@RequestBody ChatInfo chat) {

        MessageProto.MessageProtocol messageProtocol = MessageProto.MessageProtocol.newBuilder()
                .setCommand(chat.getCommand())
                .setContent(chat.getContent())
                .setTime(chat.getTime())
                .setUserId(chat.getUserId())
                .build();

        // Determine the type of information
        if (MessageConstant.CHAT.equals(chat.getCommand())) {
            for (Map.Entry<String, Channel> entry : channelMap.getConnectMap().entrySet()) {
                // Bulk
                if (!entry.getKey().equals(chat.getUserId())) {
                    logger.info("----> server {} sends message to client，news from userId: {}", entry.getKey(), chat.getUserId());
                    // It must be serialized according to the receiver's parsing method
                    entry.getValue().writeAndFlush(messageProtocol);
                }
            }
        }
    }
}
