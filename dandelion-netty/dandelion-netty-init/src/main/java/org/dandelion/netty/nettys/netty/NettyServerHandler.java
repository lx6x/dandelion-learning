package org.dandelion.netty.nettys.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * TODO netty server data deal with
 *
 * @author L
 * @version 1.0
 * @date 2022/4/27 17:14
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // buf.readableBytes() 获取缓冲区中可读的字节数
        // 根据可读字节，创建数组
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("The time server(Thread:" + Thread.currentThread() + ") receive order : " + message);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(message) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";

        ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes(StandardCharsets.UTF_8));
        // 将待发送的消息放到发送缓存数组中
        ctx.writeAndFlush(resp);
    }
}
