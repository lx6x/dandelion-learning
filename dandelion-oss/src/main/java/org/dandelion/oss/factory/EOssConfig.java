package org.dandelion.oss.factory;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/12/8 18:22
 */
public enum EOssConfig {

    OSS_CN_HANGZHOU("oss-cn-hangzhou.aliyuncs.com"),
    OSS_CN_BEIJING("oss-cn-beijing.aliyuncs.com");

    private String endpoint;

    EOssConfig(String endpoint) {
        this.endpoint = endpoint;
    }
}
