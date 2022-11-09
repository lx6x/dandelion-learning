package org.dandelion.netty.im.utils;

import org.dandelion.netty.im.config.SystemServerConfig;
import org.dandelion.netty.im.factory.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * TODO init register message to zk
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 16:53
 */
public class RegisterToZk implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RegisterToZk.class);

    private final SystemServerConfig systemConfiguration;

    private final ZkClientUtil zkClientUtil;

    public RegisterToZk() {
        this.systemConfiguration = SpringBeanFactory.getBean(SystemServerConfig.class);
        this.zkClientUtil = SpringBeanFactory.getBean(ZkClientUtil.class);
    }


    @Override
    public void run() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            logger.info("----> server ip: {}", ip);
            String root = this.systemConfiguration.getRoot();
            Integer nettyPort = this.systemConfiguration.getNettyPort();
            Integer serverPort = this.systemConfiguration.getServerPort();

            // init create root node
            zkClientUtil.createRootNode(root);
            String serverPath = root + "/" + ip + ":" + nettyPort + ":" + serverPort;
            logger.info("----> server path: {}", serverPath);
            try {
                zkClientUtil.createEphemeralNode(serverPath);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("----> zk register fail");
            }
            logger.info("----> zk register success, info: {}", serverPath);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
