package org.dandelion.thread.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * TODO ftp 工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 16:14
 */
public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    private FTPClient ftpClient;

    public FtpUtils(String hostname, Integer port, String username, String password) throws Exception {
        this.ftpClient = initFtpClient(hostname, port, username, password);
    }

    public FtpUtils() {

    }


    public FTPClient getFTPClient() {
        return this.ftpClient;
    }

    /**
     * 初始ftp服务
     *
     * @param hostname 连接地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @author L
     */
    private FTPClient initFtpClient(String hostname, Integer port, String username, String password) throws Exception {
        FTPClient ftpClient = new FTPClient();
        // 设置ftp字符集
        ftpClient.setControlEncoding("utf-8");
        // 设置连接超时时间
        ftpClient.setConnectTimeout(1000 * 30);
        // 设置被动模式，文件传输端口设置
        ftpClient.enterLocalPassiveMode();
        try {
            System.out.println("connecting...ftp服务器:" + hostname + ":" + port);
            // 连接ftp服务器
            ftpClient.connect(hostname, port);
            // 登录ftp服务器
            ftpClient.login(username, password);
            // 是否成功登录服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.info("connect failed...ftp服务器: {}", hostname + ":" + port);
            }
            logger.info("connect successfu...ftp服务器: {}", hostname + ":" + port);
            return ftpClient;
        } catch (IOException e) {
            logger.error("ftp服务器连接失败：", e);
        }
        return null;
    }

    public void close() throws IOException {
        ftpClient.disconnect();
    }
}
