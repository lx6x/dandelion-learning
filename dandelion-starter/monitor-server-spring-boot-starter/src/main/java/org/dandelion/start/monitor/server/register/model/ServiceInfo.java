package org.dandelion.start.monitor.server.register.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lx6x
 * @date 2023/12/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo implements Serializable {

    private String name;

    private String host;

    private String pid;

    private String collectionId;
}

