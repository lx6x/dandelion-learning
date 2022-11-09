package org.dandelion.scheduling.rabbitmq;

/**
 * TODO rabbit 常量
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/02 23:35
 */
public interface RabbitProperties {

    class Constant {
        // mq 连接地址
        public static final String IP_ADDRESS = "192.168.14.136";
        // mq 服务默认端口
        public static final int PORT = 5672;
        // 用户名
        public static final String USER_NAME = "rabbitmq";
        // 密码
        public static final String PASSWORD = "rabbitmq";
    }
}
