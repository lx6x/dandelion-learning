package org.dandelion.netty.im.hadle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.dandelion.netty.im.constant.MessageConstant;
import org.dandelion.netty.im.protocol.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO actual information processing class
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/15 17:05
 */
public class ImServerHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ImServerHandle.class);

    private final ChannelMap channelMap = ChannelMap.newInstance();

    /**
     * identification key
     */
    private final AttributeKey<String> userIdKey = AttributeKey.valueOf("userId");

    /**
     * invoked when the current channel has read a message from the peer
     *
     * @param msg message
     * @author L
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;
            logger.info("----> server get message: {}", message);

            String command = message.getCommand();
            if (MessageConstant.LOGIN.equals(command)) {
                logger.info("----> login");
                String userId = message.getUserId();
                Channel channel = ctx.channel();
                // set userId attributes
                channel.attr(userIdKey).set(userId);
                // userId binding channel
                channelMap.putClient(userId, channel);
            }
        } finally {
            // explicit release
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * Connection triggers normally
     *
     * @param ctx channel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("-----------------------");
    }

    /**
     * Disconnect trigger
     *
     * @param ctx channel
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("+++++++++++++++++++++++");
    }
}
