package org.dandelion.netty.beat.server.example;

import io.netty.channel.ChannelId;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liujunfei
 * @date 2023/5/16
 */
public class ClientNioSocketChannel extends NioSocketChannel {

    @Override
    protected ChannelId newId() {
        return new ChannelId() {
            @Override
            public String asShortText() {
                try {
                    return InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String asLongText() {
                try {
                    return InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public int compareTo(ChannelId o) {
                return 0;
            }
        };
    }
}
