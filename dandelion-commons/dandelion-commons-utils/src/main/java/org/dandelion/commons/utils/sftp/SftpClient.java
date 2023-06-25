package org.dandelion.commons.utils.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-29
 */
public class SftpClient implements AutoCloseable {
    private final SftpFactory sftpFactory;
    GenericObjectPool<ChannelSftp> objectPool;

    /**
     * 构造方法1
     *
     * @param properties
     * @param poolConfig
     */
    public SftpClient(SftpProperties properties, GenericObjectPoolConfig<ChannelSftp> poolConfig) {
        this.sftpFactory = new SftpFactory(properties);
        objectPool = new GenericObjectPool<>(this.sftpFactory, poolConfig);
    }

    /**
     * 构造方法2
     *
     * @param properties
     */
    public SftpClient(SftpProperties properties) {
        this.sftpFactory = new SftpFactory(properties);
        SftpProperties.PoolConfig config = properties.getPool();
        // 默认池化配置
        if (Objects.isNull(config)) {
            objectPool = new GenericObjectPool<>(this.sftpFactory);
        } else {
            // 自定义池化配置
            GenericObjectPoolConfig<ChannelSftp> poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setMaxIdle(config.getMaxIdle());
            poolConfig.setMaxTotal(config.getMaxTotal());
            poolConfig.setMinIdle(config.getMinIdle());
            poolConfig.setTestOnBorrow(config.isTestOnBorrow());
            poolConfig.setTestOnCreate(config.isTestOnCreate());
            poolConfig.setTestOnReturn(config.isTestOnReturn());
            poolConfig.setTestWhileIdle(config.isTestWhileIdle());
            poolConfig.setBlockWhenExhausted(config.isBlockWhenExhausted());
//            poolConfig.setMaxWait(Duration.ofMillis(config.getMaxWaitMillis()));
//            poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(config.getTimeBetweenEvictionRunsMillis()));
            objectPool = new GenericObjectPool<>(this.sftpFactory, poolConfig);
        }
    }

    /**
     * 销毁资源
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        // 销毁链接池
        if (Objects.nonNull(this.objectPool)) {
            if (!this.objectPool.isClosed()) {
                this.objectPool.close();
            }
        }
        this.objectPool = null;
        // 销毁sftpFactory
        if (Objects.nonNull(this.sftpFactory)) {
            this.sftpFactory.close();
        }
    }

    /**
     * 上传文件
     *
     * @param srcFilePath
     * @param targetDir
     * @param targetFileName
     * @return
     */
    public boolean uploadFile(String srcFilePath, String targetDir, String targetFileName) {
        return uploadFile(srcFilePath, targetDir, targetFileName, null);
    }

    /**
     * 上传文件
     *
     * @param srcFilePath
     * @param targetDir
     * @param targetFileName
     * @param monitor
     * @return
     */
    public boolean uploadFile(String srcFilePath, String targetDir, String targetFileName, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            // 从链接池获取对象
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, targetDir)) {
                mkdirs(channelSftp, targetDir);
            }
            channelSftp.cd(targetDir);
            // 上传文件
            if (Objects.nonNull(monitor)) {
                channelSftp.put(srcFilePath, targetFileName, monitor);
            } else {
                channelSftp.put(srcFilePath, targetFileName);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("upload file fail");
        } finally {
            if (Objects.nonNull(channelSftp)) {
                // 返还对象给链接池
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 上传文件到目标文件夹
     *
     * @param in
     * @param targetDir
     * @param targetFileName
     * @return
     */
    public boolean uploadFile(InputStream in, String targetDir, String targetFileName) {
        return uploadFile(in, targetDir, targetFileName, null);
    }

    /**
     * 上传文件，添加进度监视器
     *
     * @param in
     * @param targetDir
     * @param targetFileName
     * @param monitor
     * @return
     */
    public boolean uploadFile(InputStream in, String targetDir, String targetFileName, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, targetDir)) {
                mkdirs(channelSftp, targetDir);
            }
            channelSftp.cd(targetDir);
            if (Objects.nonNull(monitor)) {
                channelSftp.put(in, targetFileName, monitor);
            } else {
                channelSftp.put(in, targetFileName);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("upload file fail");
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile
     * @param targetFilePath
     * @return
     */
    public boolean downloadFile(String remoteFile, String targetFilePath) {
        return downloadFile(remoteFile, targetFilePath, null);
    }

    /**
     * 下载目标文件到本地
     *
     * @param remoteFile
     * @param targetFilePath
     * @return
     */
    public boolean downloadFile(String remoteFile, String targetFilePath, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, remoteFile)) {
                // 不用下载了
                return false;
            }
            File targetFile = new File(targetFilePath);
            try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                if (Objects.nonNull(monitor)) {
                    channelSftp.get(remoteFile, outputStream, monitor);
                } else {
                    channelSftp.get(remoteFile, outputStream);
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("upload file fail");
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile
     * @param outputStream
     * @return
     */
    public boolean downloadFile(String remoteFile, OutputStream outputStream) {
        return downloadFile(remoteFile, outputStream, null);
    }

    /**
     * 下载文件
     *
     * @param remoteFile
     * @param outputStream
     * @param monitor
     * @return
     */
    public boolean downloadFile(String remoteFile, OutputStream outputStream, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, remoteFile)) {
                // 不用下载了
                return false;
            }
            if (Objects.nonNull(monitor)) {
                channelSftp.get(remoteFile, outputStream, monitor);
            } else {
                channelSftp.get(remoteFile, outputStream);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("upload file fail");
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param channelSftp
     * @param dir
     * @return
     */
    protected boolean mkdirs(ChannelSftp channelSftp, String dir) {
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
    }

    /**
     * 查找目录下的所有文件
     *
     * @param remoteFileDirectory
     * @return
     */
    public List<String> findFiles(String remoteFileDirectory) {
        List<String> listFile = new ArrayList<>();
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            channelSftp.cd(remoteFileDirectory.replaceAll("\\\\", "/"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Vector<?> vector;
        try {
            vector = channelSftp.ls(remoteFileDirectory.replaceAll("\\\\", "/"));
            for (Object obj : vector) {
                if (obj != null) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                    if (!entry.getAttrs().isDir()) {
                        listFile.add(entry.getFilename());
                    }
                }
            }
        } catch (SftpException e) {
            throw new RuntimeException(e);
        } finally {
            this.objectPool.returnObject(channelSftp);
        }
        return listFile;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param channelSftp
     * @param dir
     * @return
     */
    protected boolean exist(ChannelSftp channelSftp, String dir) {
        try {
            channelSftp.lstat(dir);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
