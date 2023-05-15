package org.dandelion.netty.beat.server.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class ClientHandleInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(0,10,0))
                .addLast(new HeartbeatDecoder())
                .addLast(new ClientSimpleHandle())
        ;
    }
}
