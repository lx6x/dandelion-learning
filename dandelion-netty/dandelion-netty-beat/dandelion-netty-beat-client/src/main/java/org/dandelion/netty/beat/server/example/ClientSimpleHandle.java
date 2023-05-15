package org.dandelion.netty.beat.server.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.dandelion.netty.common.bean.BeatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class ClientSimpleHandle extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(ClientSimpleHandle.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                logger.info("已经10秒没收到消息了");
                //向服务端发送消息
                BeatInfo beatInfo = new BeatInfo();
                beatInfo.setId(1L);
                beatInfo.setContent("ping");

                ctx.writeAndFlush(beatInfo).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }

        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        logger.info("客户端收到消息：{}", msg.toString(CharsetUtil.UTF_8));
    }
}
