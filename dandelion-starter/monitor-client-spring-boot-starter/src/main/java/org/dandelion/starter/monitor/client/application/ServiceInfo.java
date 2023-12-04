package org.dandelion.starter.monitor.client.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 参考 SpringBoot Admin
 * 注册信息
 *
 * @author lx6x
 * @date 2023/11/29
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
