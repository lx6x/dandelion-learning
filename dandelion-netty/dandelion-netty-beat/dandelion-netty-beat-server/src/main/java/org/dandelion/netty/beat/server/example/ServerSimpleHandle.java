package org.dandelion.netty.beat.server.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.dandelion.netty.common.bean.BeatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 使用 IdleStateHandler 来实现连接管理，当连接空闲时间太长（没有发送、接收消息）时则会触发一个事件，我们便可在该事件中实现心跳机制
 *
 * @author liujunfei
 * @date 2023/5/15
 */
public class ServerSimpleHandle extends SimpleChannelInboundHandler<BeatInfo> {

    private static final Logger logger = LoggerFactory.getLogger(ServerSimpleHandle.class);
    private static final ByteBuf HEART_BEAT = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(new BeatInfo(123456L, "ping").toString(), CharsetUtil.UTF_8));

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BeatInfo msg) throws Exception {
        logger.info("收到 client： {} ", msg.toString());
        NettySocketHolder.put(msg.getId(), (NioSocketChannel) ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 判断当前的状态
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                logger.info("已经5秒没有收到信息！");
                //向客户端发送消息
                ctx.writeAndFlush(HEART_BEAT).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettySocketHolder.remove((NioSocketChannel) ctx.channel());
    }
}
