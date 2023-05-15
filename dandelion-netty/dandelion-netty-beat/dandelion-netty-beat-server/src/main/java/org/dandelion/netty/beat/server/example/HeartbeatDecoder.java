package org.dandelion.netty.beat.server.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.dandelion.netty.common.bean.BeatInfo;

import java.util.List;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class HeartbeatDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        long id = byteBuf.readLong();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String content = new String(bytes);
        BeatInfo beatInfo = new BeatInfo();
        beatInfo.setId(id);
        beatInfo.setContent(content);
        list.add(beatInfo);
    }
}
