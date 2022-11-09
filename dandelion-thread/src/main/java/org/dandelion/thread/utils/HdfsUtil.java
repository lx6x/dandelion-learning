package org.dandelion.thread.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsCreateModes;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO hadoop -> hdfs 操作工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/12/20 9:51
 */
public class HdfsUtil {

    private static String path = "hdfs://192.168.80.100:9000/";

    /**
     * 获取HDFS文件系统对象
     *
     * @return FileSystem
     * @author L
     */
    public static FileSystem getFileSystem() throws Exception {

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", path);
//        configuration.set("dfs.permissions", "false");

        // 客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从jvm中获取一个参数作为自己的用户身份
        // DHADOOP_USER_NAME=hadoop
        // 也可以在构造客户端fs对象时，通过参数传递进去
        return FileSystem.get(new URI(path), configuration, "hadoop");
    }

    /**
     * 判断HDFS文件是否存在
     *
     * @param path 文件路径
     * @return bool
     * @author L
     */
    public static boolean existFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        return fs.exists(srcPath);
    }

    /**
     * 在HDFS创建文件夹
     *
     * @param path 目录
     * @return bool
     * @throws Exception
     */
    public static boolean mkdir(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (existFile(path)) {
            // 文件存在不能创建
            return false;
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        boolean isOk = fs.mkdirs(srcPath);
        fs.close();
        return isOk;
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path 目录
     * @return list
     * @author L
     */
    public static List<Map<String, Object>> readPathInfo(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path newPath = new Path(path);
        FileStatus[] statusList = fs.listStatus(newPath);
        List<Map<String, Object>> list = new ArrayList<>();
        if (null != statusList && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                Map<String, Object> map = new HashMap<>();
                map.put("filePath", fileStatus.getPath());
                map.put("fileStatus", fileStatus.toString());
                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * HDFS创建文件
     *
     * @param path     目录
     * @param bytes    文件
     * @param fileName 文件名称
     * @author L
     */
    public static void createFile(String path, byte[] bytes, String fileName) throws Exception {
        if (StringUtils.isEmpty(path) || null == bytes) {
            return;
        }
        FileSystem fs = getFileSystem();
        // 上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path(path + "/" + fileName);
        // 打开一个输出流
        FsPermission dirDefault = FsPermission.getDirDefault();

        FsPermission fsPermission = new FsPermission((short) 444);

//        FSDataOutputStream outputStream = fs.create(newPath);
        FSDataOutputStream outputStream = fs.createNonRecursive(newPath,
//                fsPermission ,
                FsCreateModes.applyUMask(fsPermission, FsPermission.getUMask(fs.getConf())),
                true,
                fs.getConf().getInt("io.file.buffer.size", 4096),
                fs.getDefaultReplication(newPath),
                fs.getDefaultBlockSize(newPath),
                (Progressable) null
        );
        outputStream.write(bytes);
        outputStream.close();
        fs.close();
    }

    /**
     * 上传HDFS文件 (未测通)
     *
     * @param path       上传路径
     * @param uploadPath 目标路径
     * @author L
     */
    public static void uploadFile(String path, String uploadPath) throws Exception {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
            return;
        }
        FileSystem fs = getFileSystem();
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(uploadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyFromLocalFile(false, clientPath, serverPath);
        fs.close();
    }

    /**
     * 删除HDFS文件
     *
     * @param path 文件全路径
     * @return bool
     * @author L
     */
    public static boolean deleteFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!existFile(path)) {
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isOk = fs.deleteOnExit(srcPath);
        fs.close();
        return isOk;
    }


    public static void main(String[] args) throws Exception {
        // 获取指定文件目录下的文件信息
//        List<Map<String, Object>> mapList = readPathInfo("/crh/test01");
//        System.err.println(JSONObject.toJSONString(mapList));

//        boolean b = existFile("/CCCCCCCCCCCCC/aaaa/1111.txt");
//        System.err.println(b);

        FtpUtils ftpUtils = new FtpUtils("192.168.80.100", 21, "ftpuser", "123456");
        FTPClient ftpClient = ftpUtils.getFTPClient();
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory("/var/ftp/ftpuser/bieyong");
        //设置为二进制传输
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
            String name = ftpFile.getName();

            InputStream inputStream = ftpClient.retrieveFileStream(name);
            byte[] bytes = inputStreamToByte(inputStream);

            createFile("/CCCCCCCCCCCCC/", bytes, name);
        }

//        File file = new File("D:\\ftp\\1111.txt");
//        byte[] bytesByFile = getBytesByFile(file);
//        createFile("/CCCCCCCCCCCCC/aaaa", bytesByFile, file.getName());
//
//        uploadFile("ftp://192.168.80.100:21/var/ftp/ftpuser/1.txt", "/test");

//        boolean b = deleteFile("/test/bieyong");
//        System.err.println(b);

        // 创建文件目录
//        boolean mkdir = mkdir("/BBBBBBBBBBBBBBBBBBB");
//        System.err.println(mkdir);
    }

    public static byte[] inputStreamToByte(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static byte[] getBytesByFile(File file) {
        try {
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
