package org.dandelion.netty.nettys.line;

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
public class NettyLineServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        System.out.println("The time server(Thread:" + Thread.currentThread() + ") receive order : " + message + ". the counter is : " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(message) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        // 写回客户端数据也许带上换行符
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes(StandardCharsets.UTF_8));
        // 将待发送的消息放到发送缓存数组中
        ctx.writeAndFlush(resp);
    }
}
