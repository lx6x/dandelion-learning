package org.dandelion.netty.nettys.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * TODO netty client data deal with
 *
 * @author L
 * @version 1.0
 * @date 2022/4/27 17:26
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 向服务端发送指令
     *
     * @param ctx 未知
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);

//        粘包测试
//        ByteBuf firstMessage2 = Unpooled.buffer(req.length);
//        firstMessage2.writeBytes(req);
//        ctx.writeAndFlush(firstMessage2);
    }

    /**
     * 接收服务器的响应
     *
     * @param ctx 未知
     * @param msg 数据包
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        // buf.readableBytes():获取缓冲区中可读的字节数；
        // 根据可读字节数创建数组
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("Now is : " + body);
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
