package org.dandelion.data.sharding.config;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

/**
 * TODO 自定义 hash 主键生成策略
 *
 * @author L
 * @version 1.0
 * @date 2022/4/13 14:20
 */
public class HashKeyGenerator implements ShardingKeyGenerator {
    @Override
    public Comparable<?> generateKey() {
        return null;
    }

    @Override
    public String getType() {
        return "HASH";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
