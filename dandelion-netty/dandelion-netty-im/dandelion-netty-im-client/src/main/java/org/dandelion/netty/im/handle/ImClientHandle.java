package org.dandelion.netty.im.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.dandelion.netty.common.protocol.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO actual information processing class
 *
 * @author L
 * @version 1.0
 * @date 2022/5/18 11:10
 */
public class ImClientHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ImClientHandle.class);

    /**
     * This method is called with the received message whenever new data is received from the client
     *
     * @param ctx channel
     * @param msg messages
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;

            logger.info("client receives message: {}", message.getContent());
        } finally {
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
    }
}
