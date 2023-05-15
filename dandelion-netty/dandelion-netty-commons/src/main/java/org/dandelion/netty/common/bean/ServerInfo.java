package org.dandelion.netty.common.bean;

import java.io.Serializable;

/**
 * TODO server connect param
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/16 20:27
 */
public class ServerInfo implements Serializable {

    private static final long serialVersionUID = 1681981071608979404L;

    private String ip;
    private Integer nettyPort;
    private Integer httpPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNettyPort() {
        return nettyPort;
    }

    public void setNettyPort(Integer nettyPort) {
        this.nettyPort = nettyPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }
}
