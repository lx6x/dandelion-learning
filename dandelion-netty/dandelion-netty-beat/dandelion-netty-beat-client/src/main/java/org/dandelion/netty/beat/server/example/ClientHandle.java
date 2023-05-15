package org.dandelion.netty.beat.server.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class ClientHandle extends ChannelInboundHandlerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(ClientHandle.class);

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request",
            CharsetUtil.UTF_8));

    /**
     * 每当从客户端接收到新数据时，将使用接收到的消息调用此方法
     *
     * @param ctx channel
     * @param msg messages
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BeatInfo beatInfo = (BeatInfo) msg;
        System.out.println(beatInfo.toString());
    }

    /**
     * 连接触发正常
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
            if (IdleState.WRITER_IDLE == event.state()) {
                //如果写通道处于空闲状态,就发送心跳命令
                BeatInfo beatInfo=new BeatInfo();
                beatInfo.setId(123L);
                beatInfo.setContent(String.valueOf(System.currentTimeMillis()));
                ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());

            }

        }
    }
}
