package org.dandelion.starter.monitor.client.register;

/**
 * 注册客户端应用程序
 *
 * @author lx6x
 * @date 2023/11/29
 */
public interface ApplicationRegister {

    /**
     * 注册
     */
    boolean register();

    /**
     * 注销
     */
    void deregister();

    /**
     * 当前服务注册server返回id
     */
    String getRegisteredId();
}
