package org.dandelion.netty.beat.server.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.dandelion.netty.common.bean.BeatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class ServerHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandle.class);

    /**
     * 当前通道从对等端读取消息时调用
     *
     * @param msg message
     * @author L
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BeatInfo beatInfo = (BeatInfo) msg;
        System.out.println(beatInfo.toString());
    }

    /**
     * 连接正常触发
     *
     * @param ctx channel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("---- 连接成功");
    }

    /**
     * 断开触发
     *
     * @param ctx channel
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("---- 连接断开");
    }

    /**
     * 心跳判断
     *
     * @param ctx .
     * @param evt .
     * @author liujunfei
     * @date 2023/5/15
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                //如果读通道处于空闲状态，说明没有接收到心跳命令
                System.out.println("5 秒了还未接收到信息");
            }

        }
    }
}
