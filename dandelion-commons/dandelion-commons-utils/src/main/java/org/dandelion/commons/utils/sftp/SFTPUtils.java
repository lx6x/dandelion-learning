package org.dandelion.commons.utils.sftp;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;
import org.dandelion.commons.utils.enums.FtpErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-28
 */
public class SFTPUtils {
    private final static Logger logger = LoggerFactory.getLogger(SFTPUtils.class);

    private final static int CLIENT_TIMEOUT = 3600 * 1000;

    ChannelSftp channelSftp = null;     //所有操作公用一个Channel
    private String serverIP;
    private Integer port;
    private String username;
    private String password;
    private String encoding = "UTF-8";

    public SFTPUtils(String serverIP, Integer port, String username, String password) {
        this.serverIP = serverIP;
        if (port != null) {
            this.port = port;
        }
        this.username = username;
        this.password = password;
    }

    public static SFTPUtils newInstance(String serverIP, Integer port, String username, String password) {
        return new SFTPUtils(serverIP, port, username, password);
    }

    /**
     * (50+50) * 0.05 = 50* 0.05 + 50* 0.05
     *
     * @return
     */
    public void connectFtp() throws Exception {
        logger.info("SftpUtils start beginning");
        Session session;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, serverIP, port);
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

            channelSftp = (ChannelSftp) channel;

        } catch (Exception e) {
            logger.info(FtpErrorCode.CONNECT_SERVER_FAILER.getErrorDesc());
            throw e;
        }
    }


    public void disConnect() throws Exception {
        logger.info("SftpUtils end beginning...");
        if (channelSftp != null) {
            Session session = null;
            try {
                session = channelSftp.getSession();
            } catch (JSchException e) {
                logger.info(FtpErrorCode.DISCONNECT_SERVER_GET_CONNECT_FAILER.getErrorDesc());
                throw e;
            } finally {
                if (session != null) {
                    session.disconnect();
                }
            }
        }
    }

    /**
     * 将本地文件上传到远程ftp服务器某目录中
     *
     * @param localFile  本地文件全路径
     * @param remoteFile 远程文件全路径
     */
    public void uploadFile(String localFile, String remoteFile) throws Exception {
        logger.info("SftpUtils upload beginning");
        InputStream inputStream = null;
        logger.info("1. 连接ftp服务器");
        this.connectFtp();
        logger.info("2. 转换本地文件为输入流");
        try {
            inputStream = new FileInputStream(localFile);
        } catch (FileNotFoundException e) {
            this.disConnect();
            logger.info(FtpErrorCode.GET_LOCAL_FILE_INPUT_STREAM.getErrorDesc());
            throw e;
        }
        logger.info("3. 创建远程目录文件夹");
        String fileNameAbsolute = remoteFile.replaceAll("\\\\", "/");
        String remoteDirectory = fileNameAbsolute.substring(0, fileNameAbsolute.lastIndexOf("/"));

        logger.info("remoteDirectory" + remoteDirectory);

        mkdirs(remoteDirectory);

        logger.info("4. 保存输入流到sftp服务器");
        try {
            channelSftp.put(inputStream, remoteFile.replaceAll("\\\\", "/"));
        } catch (SftpException e) {
            logger.info(FtpErrorCode.UPLOAD_FILE_FAILER.getErrorDesc());
            throw e;
        } finally {
            this.disConnect();
        }
    }

    /**
     * 将内存数据转换为输入流上传
     *
     * @param localInputStream 本地输入流
     * @param remoteFile       远程文件全路径
     */
    public void uploadFile(InputStream localInputStream, String remoteFile) throws Exception {
        logger.info("SftpUtils upload beginning");
        logger.info("1. 连接ftp服务器");
        this.connectFtp();

        logger.info("2. 创建远程目录文件夹");
        String fileNameAbsolute = remoteFile.replaceAll("\\\\", "/");
        String remoteDirectory = fileNameAbsolute.substring(0, fileNameAbsolute.lastIndexOf("/"));
        mkdirs(remoteDirectory);

        logger.info("4. 保存输入流到sftp服务器");
        try {
            channelSftp.put(localInputStream, remoteFile.replaceAll("\\\\", "/"));
        } catch (SftpException e) {
            logger.info(FtpErrorCode.UPLOAD_FILE_FAILER.getErrorDesc());
            throw e;
        } finally {
            this.disConnect();
        }
    }

    /**
     * 如果远程目录不存在，则创建远程目录
     *
     * @param remoteDirectory /abc/sdf/sdf/adf
     */
    void mkdirs(String remoteDirectory) throws Exception {
        this.connectFtp();
        try {
            channelSftp.cd(remoteDirectory);
        } catch (SftpException e) {
            String[] dirs = remoteDirectory.replaceAll("\\\\", "/").split("/"); //{abc,sdf,sdf,adf}
            String tempPath = "";
            for (String dir : dirs) {
                if (StringUtils.isEmpty(dir)) {
                    continue;
                }

                //此处不能使用File.separator
                tempPath += "/" + dir;
                try {
                    channelSftp.cd(tempPath);
                } catch (SftpException sftpException) {
                    try {
                        channelSftp.mkdir(tempPath);
                        channelSftp.cd(tempPath);
                    } catch (Exception exception) {
                        this.disConnect();
                        logger.info(FtpErrorCode.CREATE_DIRECTORY_FAILER.getErrorDesc());
                        throw exception;
                    }
                }
            }
        }
        try {
            channelSftp.cd(remoteDirectory.replaceAll("\\\\", "/"));
        } catch (SftpException e) {
            this.disConnect();
            logger.info(FtpErrorCode.CONNECT_SERVER_FAILER.getErrorDesc());
            throw e;
        } finally {
            logger.info("4. 关闭sftp的连接");
            this.disConnect();
        }
    }

    /*protected boolean mkdirs(String dir) throws Exception {
        try {
            this.connectFtp();
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
        }finally {
            logger.info("关闭sftp的连接");
            this.disConnect();
        }
    }*/

    /**
     * @param remoteFile 远程文件————————>本系统特点是从一个目录取得
     * @param localFile  本地系统目录
     */
    public void ftpDownLoad(String remoteFile, String localFile) throws Exception {
        logger.info("SftpUtils download beginning");
        logger.info("1. 连接ftp服务器");
        this.connectFtp();
        logger.info("2. 创建本地文件输入流");
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(localFile);
        } catch (FileNotFoundException e) {
            this.disConnect();
            logger.info(FtpErrorCode.GET_LOCAL_FILE_OUTPUT_STREAM.getErrorDesc());
            throw new RuntimeException(e);
        }
        logger.info("3. 下载到本地文件/输出流");
        try {
            channelSftp.get(remoteFile.replaceAll("\\\\", "/"), outputStream);
        } catch (SftpException e) {
            this.disConnect();
            logger.info(FtpErrorCode.GET_LOCAL_FILE_OUTPUT_STREAM.getErrorDesc());
            throw e;
        } finally {
            logger.info("4. 关闭sftp的连接");
            this.disConnect();
        }
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


    public void ftpDelete(String remoteFile) throws Exception {
        logger.info("删除远端文件");
        String directory = remoteFile.replaceAll("\\\\", "/").substring(0, remoteFile.lastIndexOf("/"));
        try {
            channelSftp.cd(directory);
        } catch (SftpException e) {
            this.disConnect();
            logger.info(FtpErrorCode.DELETE_FTP_FILE_FAILER.getErrorDesc());
            throw e;
        }
//        String fileName = remoteFile.substring(remoteFile.replaceAll("\\\\","/").lastIndexOf("/") + 1);
        try {
            channelSftp.rm(remoteFile);//remove（）
        } catch (SftpException e) {
            this.disConnect();
            logger.info(FtpErrorCode.DELETE_FTP_FILE_FAILER.getErrorDesc());
            throw e;
        } finally {
            logger.info("4. 关闭sftp的连接");
            this.disConnect();
        }
    }

    /**
     * 查找目录下的所有文件
     *
     * @param remoteFileDirectory
     * @return
     */
    public List<String> findFiles(String remoteFileDirectory) throws Exception {
        logger.info("find directory files beginning...");
        List<String> listFile = new ArrayList<>();
        logger.info("远程目录为：" + remoteFileDirectory);
        try {
            channelSftp.cd(remoteFileDirectory.replaceAll("\\\\", "/"));
        } catch (SftpException e) {
            mkdirs(remoteFileDirectory.replaceAll("\\\\", "/"));
            return listFile;
        }
        Vector<?> vector = null;
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
            logger.info(FtpErrorCode.LS_REMOTE_PATH_FAILER.getErrorDesc());
            throw e;
        } finally {
            this.disConnect();
        }
        return listFile;
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
                        if (filename.contains(condition)) {
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

    /**
     * @param srcFile  源文件
     * @param destFile 目标文件
     */
    public void moveFile(String srcFile, String destFile) throws Exception {
        logger.info("1. 连接ftp服务器");
        this.connectFtp();

        logger.info("2. 效验目标文件的目录，如果没有则创建");
        int pos = destFile.lastIndexOf("/");
        this.mkdirs(destFile.substring(0, pos)); // 创建目录

        logger.info("3. 将源文件名称 重命名为 目标文件名称");
        try {
            channelSftp.rename(srcFile.replaceAll("\\\\", "/"), destFile.replaceAll("\\\\", "/"));
        } catch (SftpException e) {
            e.printStackTrace();
            logger.info(FtpErrorCode.LS_REMOTE_PATH_FAILER.getErrorDesc());
            throw e;
        } finally {
            this.disConnect();
        }
//      logger.info("4. 删除源文件");
//      this.ftpDelete(srcFile.replaceAll("\\\\","/"));
    }


    /**
     * 复制文件夹
     *
     * @param src  源文件夹
     * @param desc 目的文件夹
     */
    public void copy(String src, String desc) {
        try {
            Vector<?> ls = channelSftp.ls(src);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * stream 写入到 指定路径下
     *
     * @param inputStream
     * @param filePath
     */
    public void put(InputStream inputStream, String filePath) {
        try {
            channelSftp.put(inputStream, filePath);
        } catch (SftpException e) {
            logger.error("put" + e);
        }
    }

    public static void main(String[] args) throws Exception {
        SFTPUtils sftp = SFTPUtils.newInstance("172.16.5.103", 22, "testsftp", "lenovo@123");
        sftp.connectFtp();
        List<String> files = sftp.findConditionFiles("/crm/sync/flow", "operator_card_flow_compare_");
        System.out.println(files);
        for (String file : files) {

            InputStream inputStream = sftp.sftpWriter("/crm/sync/flow/".concat(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            br.readLine();
            while (null != (line = br.readLine())) {
                if ("".equals(line)) {
                    break;
                }
                String[] str = line.split("\\|");
                System.out.println(str[0] + "     " + str[1] + "     " + str[2] + "     " + str[3] + "     " + str[4]);
            }
        }


        sftp.disConnect();
//        sftp.mkdirs("/crm/sync/flow/1");
//        List<String> files2 = sftp.findFiles("/crm/sync/flow");
//        System.out.println(files2);

    }

/*    public static void main(String[] args) {


        try {
            int i=1/0;
            System.out.println(i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(3);
        }

    }*/

}
