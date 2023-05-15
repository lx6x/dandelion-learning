package org.dandelion.netty.beat.server.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.dandelion.netty.common.bean.BeatInfo;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class HeartbeatDecoder extends MessageToByteEncoder<BeatInfo> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BeatInfo beatInfo, ByteBuf byteBuf) throws Exception {
        byteBuf.writeLong(beatInfo.getId());
        byteBuf.writeBytes(beatInfo.getContent().getBytes());
    }
}
