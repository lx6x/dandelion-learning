package org.dandelion.netty.nettys.line;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TODO netty client data deal with
 *
 * @author L
 * @version 1.0
 * @date 2022/4/27 17:26
 */
public class NettyLineClientHandler extends ChannelInboundHandlerAdapter {

    private int counter;
    private byte[] req;

    /**
     * 向服务端发送指令
     *
     * @param ctx 未知
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message;
        //模拟一百次请求，发送重复内容
        for (int i = 0; i < 20; i++) {
            // 发送到服务端数据需要附带换行符
            req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    /**
     * 接收服务器的响应
     *
     * @param ctx 未知
     * @param msg 数据包
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = (String) msg;
        System.out.println("Now is : " + body + ". the counter is : " + ++counter);
    }

    /**
     * 异常处理
     *
     * @param ctx   未知
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 释放资源
        ctx.close();
    }
}
