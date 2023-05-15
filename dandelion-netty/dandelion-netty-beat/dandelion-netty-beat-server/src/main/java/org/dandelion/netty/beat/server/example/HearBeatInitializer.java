package org.dandelion.netty.beat.server.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class HearBeatInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                // 5秒没有读操作 将触发READER_IDLE
                .addLast(new IdleStateHandler(5, 0, 0))
                .addLast(new HeartbeatDecoder())
                .addLast(new ServerSimpleHandle());
    }
}
