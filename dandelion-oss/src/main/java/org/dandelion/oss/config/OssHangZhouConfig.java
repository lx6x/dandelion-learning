package org.dandelion.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import org.dandelion.oss.factory.IOssConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO 配置oss连接客户端
 *
 * @author L
 * @version 1.0
 * @date 2021/12/7 10:33
 */
@Configuration
public class OssHangZhouConfig implements IOssConfig {

    @Value(value = "${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value(value = "${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value(value = "${aliyun.oss.endpoint}")
    private String endpoint;

    @Bean
    public OSS ossClient() {
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();

        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        clientConfiguration.setMaxConnections(200);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        clientConfiguration.setSocketTimeout(10000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        clientConfiguration.setConnectionTimeout(10000);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        clientConfiguration.setConnectionRequestTimeout(1000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        clientConfiguration.setIdleConnectionTime(10000);
        // 设置失败请求重试次数，默认为3次。
        clientConfiguration.setMaxErrorRetry(3);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        clientConfiguration.setSupportCname(true);
        // 设置是否开启二级域名的访问方式，默认不开启。
        clientConfiguration.setSLDEnabled(true);
        // 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP。
        clientConfiguration.setProtocol(Protocol.HTTP);
        // 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
        clientConfiguration.setUserAgent("aliyun-sdk-java");
        // 设置代理服务器端口。
//        clientConfiguration.setProxyHost("<yourProxyHost>");
        // 设置代理服务器验证的用户名。
//        clientConfiguration.setProxyUsername("<yourProxyUserName>");
        // 设置代理服务器验证的密码。
//        clientConfiguration.setProxyPassword("<yourProxyPassword>");
        // 设置是否开启HTTP重定向，默认开启。
        clientConfiguration.setRedirectEnable(true);
        // 设置是否开启SSL证书校验，默认开启。
//        clientConfiguration.setVerifySSLEnable(true);

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public IOssConfig init() {
        return null;
    }
}
