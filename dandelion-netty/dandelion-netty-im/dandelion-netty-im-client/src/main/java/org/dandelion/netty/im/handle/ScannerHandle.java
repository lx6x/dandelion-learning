package org.dandelion.netty.im.handle;

import org.dandelion.netty.common.bean.ChatInfo;
import org.dandelion.netty.im.config.SystemClientConfig;
import org.dandelion.netty.common.constant.MessageConstant;
import org.dandelion.netty.common.factory.SpringBeanFactory;
import org.dandelion.netty.im.inits.InitImNettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * TODO send messages using scanner
 *
 * @author L
 * @version 1.0
 * @date 2022/5/18 11:25
 */
public class ScannerHandle implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ScannerHandle.class);

    private InitImNettyClient initImNettyClient;

    private SystemClientConfig systemClientConfig;

    public ScannerHandle() {
        this.systemClientConfig = SpringBeanFactory.getBean(SystemClientConfig.class);
        this.initImNettyClient = SpringBeanFactory.getBean(InitImNettyClient.class);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();

            if (StringUtils.isEmpty(next)) {
                logger.info("message cannot be empty");
                continue;
            }

            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setCommand(MessageConstant.CHAT);
            chatInfo.setUserId(systemClientConfig.getUserId());
            chatInfo.setContent(next);
            chatInfo.setTime(System.currentTimeMillis());
            initImNettyClient.sendMessage(chatInfo);

        }

    }
}
