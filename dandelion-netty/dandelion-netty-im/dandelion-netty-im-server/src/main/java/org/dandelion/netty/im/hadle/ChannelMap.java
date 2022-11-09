package org.dandelion.netty.im.hadle;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO store all connected channels
 *
 * @author L
 * @version 1.0
 * @date 2022/5/18 17:05
 */
public class ChannelMap {

    private static ChannelMap instance;

    /**
     * store all connected channels
     */
    private final Map<String, Channel> connectMap = new ConcurrentHashMap<>();

    private ChannelMap() {
    }

    /**
     * use singleton
     *
     * @return this
     * @author L
     */
    public static ChannelMap newInstance() {
        if (null == instance) {
            instance = new ChannelMap();
        }
        return instance;
    }

    public Map<String, Channel> getConnectMap() {
        return connectMap;
    }

    public void putClient(String userId, Channel channel){
        connectMap.put(userId, channel);
    }
}
