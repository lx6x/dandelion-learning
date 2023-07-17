//package org.dandelion.commons.utils.sftp;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
///**
// * TODO
// *
// * @author L
// * @version 1.0
// * @date 2022-12-29
// */
//@Data
//@Configuration
//@ConfigurationProperties(prefix = "sftp.config")
//public class SftpProperties {
//
//    /**
//     * 用户名
//     */
//    private String username;
//    /**
//     * 密码
//     */
//    private String password;
//    /**
//     * 主机名
//     */
//    private String host;
//    /**
//     * 端口
//     */
//    private int port;
//    /**
//     * 密钥
//     */
//    private String privateKeyFile;
//    /**
//     * 口令
//     */
//    private String passphrase;
//    /**
//     * 通道链接超时时间
//     */
//    private int timeout;
//    /**
//     * 链接池配置
//     */
//    private PoolConfig pool;
//
//    @Data
//    public static class PoolConfig {
//        //最大空闲实例数，空闲超过此值将会被销毁淘汰
//        private int maxIdle;
//        // 最小空闲实例数，对象池将至少保留2个空闲对象
//        private int minIdle;
//        //最大对象数量，包含借出去的和空闲的
//        private int maxTotal;
//        //对象池满了，是否阻塞获取（false则借不到直接抛异常）
//        private boolean blockWhenExhausted;
//        // BlockWhenExhausted为true时生效，对象池满了阻塞获取超时，不设置则阻塞获取不超时，也可在borrowObject方法传递第二个参数指定本次的超时时间
//        private long maxWaitMillis;
//        // 创建对象后是否验证对象，调用objectFactory#validateObject
//        private boolean testOnCreate;
//        // 借用对象后是否验证对象 validateObject
//        private boolean testOnBorrow;
//        // 归还对象后是否验证对象 validateObject
//        private boolean testOnReturn;
//        // 定时检查期间是否验证对象 validateObject
//        private boolean testWhileIdle;
//        //定时检查淘汰多余的对象, 启用单独的线程处理
//        private long timeBetweenEvictionRunsMillis;
//        //jmx监控，和springboot自带的jmx冲突，可以选择关闭此配置或关闭springboot的jmx配置
//        private boolean jmxEnabled;
//
//    }
//}
