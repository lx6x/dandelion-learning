package org.dandelion.start.monitor.server.register.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lx6x
 * @date 2023/12/1
 */
@Data
public class ServiceInfo implements Serializable {

    private final String name;

    private final String host;

    private final String pid;


    @Builder
    protected ServiceInfo(String name, String host, String pid) {
        this.pid = pid;
        this.host = host;
        this.name = name;
    }


}

