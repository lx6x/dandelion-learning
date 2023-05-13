package org.dandelion.scheduling.rocketmq.listener;

import org.apache.rocketmq.spring.core.RocketMQListener;

import java.util.List;

/**
 * @author liujunfei
 * @date 2023/5/12
 */
public class ConsumerListener implements RocketMQListener<List<String>> {
    @Override
    public void onMessage(List<String> strings) {

    }
}
