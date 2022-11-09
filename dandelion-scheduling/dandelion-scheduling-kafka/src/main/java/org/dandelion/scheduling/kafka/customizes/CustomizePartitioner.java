package org.dandelion.scheduling.kafka.customizes;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * TODO 自定义分区策略
 *
 * @author L
 * @version 1.0
 * @date 2021/9/22 17:14
 */
public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
