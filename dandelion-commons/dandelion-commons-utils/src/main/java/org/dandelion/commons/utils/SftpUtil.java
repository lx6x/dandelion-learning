package org.dandelion.commons.utils;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-29
 */
public class SftpUtil {

    private final static Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    private final static int CLIENT_TIMEOUT = 3600 * 1000;
    private ChannelSftp channelSftp = null;
    private final String url;
    private Integer port;
    private final String username;
    private final String password;
    private String encoding = "UTF-8";

    public SftpUtil(String url, Integer port, String username, String password) {
        this.url = url;
        if (port != null) {
            this.port = port;
        }
        this.username = username;
        this.password = password;
    }

    public void connectFtp() throws Exception {
        logger.info("SftpUtil start beginning");
        Session session;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, url, port);
            session.setTimeout(CLIENT_TIMEOUT);
            if (!StringUtils.isEmpty(password)) {
                session.setPassword(password);
            }
            Properties properties = new Properties();
            properties.setProperty("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            this.channelSftp = (ChannelSftp) channel;

        } catch (Exception e) {
            logger.info("连接sftp服务器失败");
            throw e;
        }
    }

    public static SftpUtil newInstance(String url, Integer port, String username, String password) throws Exception {
        SftpUtil sftpUtil = new SftpUtil(url, port, username, password);
        sftpUtil.connectFtp();
        return sftpUtil;
    }

    public static SftpUtil newInstance(String url, String username, String password) throws Exception {
        SftpUtil sftpUtil = new SftpUtil(url, 22, username, password);
        sftpUtil.connectFtp();
        return sftpUtil;
    }

    /**
     * 获取 channel 连接通道
     *
     * @return ChannelSftp
     */
    public ChannelSftp getChannelSftp() {
        return this.channelSftp;
    }

    /**
     * 创建文件夹
     *
     * @param dir 文件路径
     * @return 成功/失败
     */
    public boolean mkdirs(String dir) {
        try {
            String pwd = channelSftp.pwd();
            if (StringUtils.contains(pwd, dir)) {
                return true;
            }
            String relativePath = StringUtils.substringAfter(dir, pwd);
            String[] dirs = StringUtils.splitByWholeSeparatorPreserveAllTokens(relativePath, "/");
            for (String path : dirs) {
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                try {
                    channelSftp.cd(path);
                } catch (SftpException e) {
                    channelSftp.mkdir(path);
                    channelSftp.cd(path);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
        // 需要手动关闭连接
    }

    /**
     * 读取远程文件中的内容，并返回流
     *
     * @param dir 文件全路径 /a/a.text
     * @return stream
     */
    public InputStream sftpWriter(String dir) {
        InputStream is = null;
        try {
            is = this.channelSftp.get(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 需要手动关闭连接
        return is;
    }

    /**
     * 断开 session 连接
     *
     * @throws Exception
     */
    public void disConnect() throws Exception {
        logger.info("SftpUtil end beginning...");
        if (channelSftp != null) {
            Session session = null;
            try {
                session = channelSftp.getSession();
            } catch (JSchException e) {
                logger.info("断开sftp服务器获取连接失败");
                throw e;
            } finally {
                if (session != null) {
                    session.disconnect();
                }
            }
        }
    }

    /**
     * 获取ftp服务器下某个文件下的  符合条件的文件
     *
     * @param path      目录路径
     * @param condition 筛选条件
     * @return list
     */
    public List<String> findConditionFiles(String path, String condition) {
        List<String> listFile = new ArrayList<>();
        try {

            this.channelSftp.cd(path.replaceAll("\\\\", "/"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Vector<?> vector;
        try {
            vector = this.channelSftp.ls(path.replaceAll("\\\\", "/"));
            for (Object obj : vector) {
                if (obj != null) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                    if (!entry.getAttrs().isDir()) {
                        String filename = entry.getFilename();
                        if(filename.contains(condition)){
                            listFile.add(filename);
                        }
                    }
                }
            }
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        return listFile;
    }
}
