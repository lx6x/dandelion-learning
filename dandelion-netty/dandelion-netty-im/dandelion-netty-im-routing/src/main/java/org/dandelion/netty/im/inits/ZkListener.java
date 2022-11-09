package org.dandelion.netty.im.inits;

import org.I0Itec.zkclient.ZkClient;
import org.dandelion.netty.im.config.SystemRoutingConfig;
import org.dandelion.netty.im.utils.ZkClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TODO start monitoring zk
 *
 * @author L
 * @version 1.0
 * @date 2022/5/16 17:27
 */
@Component
@Order(value = 2)
public class ZkListener implements CommandLineRunner {

    @Autowired
    private SystemRoutingConfig systemRoutingConfig;

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZkClientUtil zkClientUtil;

    @Override
    public void run(String... args) throws Exception {

        zkClient.subscribeChildChanges(systemRoutingConfig.getRoot(), (s, list) -> {
            zkClientUtil.setAllNode(list);
        });
    }
}
