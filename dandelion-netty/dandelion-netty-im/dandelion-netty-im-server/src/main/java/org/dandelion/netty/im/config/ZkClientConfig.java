package org.dandelion.netty.im.config;

import org.I0Itec.zkclient.ZkClient;
import org.dandelion.netty.common.utils.ZkClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/5/17 16:22
 */
@Configuration
public class ZkClientConfig {

    @Value("${im.zk.adder}")
    private String adder;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(adder);
    }

    @Bean
    public ZkClientUtil zkClientUtil(){
        return new ZkClientUtil();
    }
}
