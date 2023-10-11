package org.dandelion.netty.beat.server.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @date 2023/5/15
 */
@Component
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Resource
    private ServerHandle serverHandle;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
//                .addLast(new HttpServerCodec())
                // 5秒没有读操作 将触发READER_IDLE
                .addLast("idleStateHandler", new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                .addLast(serverHandle);
    }
}
