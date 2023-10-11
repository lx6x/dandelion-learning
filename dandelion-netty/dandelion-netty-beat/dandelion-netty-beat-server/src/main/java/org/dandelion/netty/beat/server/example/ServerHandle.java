package org.dandelion.netty.beat.server.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @date 2023/5/15
 */
@Component
public class ServerHandle extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandle.class);

    /**
     * 当前通道从对等端读取消息时调用
     *
     * @param msg message
     * @author L
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务端成功收到信息 " + msg);
    }

    /**
     * 连接正常触发
     *
     * @param ctx channel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelId id = ctx.channel().id();
        logger.info("---- 连接成功 {}", id);
    }

    /**
     * 断开触发
     *
     * @param ctx channel
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelId id = ctx.channel().id();
        logger.info("---- 连接断开 " + id);
    }

    /**
     * 心跳判断
     *
     * @param ctx .
     * @param evt .
     * @date 2023/5/15
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                //如果读通道处于空闲状态，说明没有接收到心跳命令
                System.out.println("有一段时间没有 收到 数据");
            } else if (IdleState.WRITER_IDLE == event.state()) {
                System.out.println("有一段时间没有 发送 数据");
            } else if (IdleState.ALL_IDLE == event.state()) {
                System.out.println("有一段时间没有 接收或发送 数据");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
