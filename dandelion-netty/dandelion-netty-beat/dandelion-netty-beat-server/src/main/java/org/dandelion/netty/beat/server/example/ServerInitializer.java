package org.dandelion.netty.beat.server.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @date 2023/5/15
 */
public class ServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                // 5秒没有读操作 将触发READER_IDLE
                .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                .addLast(new ServerHandle());
    }
}
