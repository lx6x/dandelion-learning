package org.dandelion.thread.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO ftp
 *
 * @author L
 * @version 1.0
 * @date 2021/12/17 11:32
 */
public class FtpUtil {

    /**
     * ftp服务器地址
     */
    private static final String hostname = "192.168.80.100";
    /**
     * ftp服务器端口号
     */
    private static final Integer port = 21;
    /**
     * ftp登录账号
     */
    private static final String username = "ftpuser";
    /**
     * ftp登录密码
     */
    private static final String password = "123456";
    /**
     * FTP服务器文件目录
     */
    private static final String pathname = "";
    /**
     * 下载后的文件路径
     */
    public static final String localpath = "D:/ftp";

    public static final FTPClient ftpClient = new FTPClient();

    public static void main(String[] args) {
        downloadFile();
    }

    /**
     * 获取ftp文件
     */
    private static boolean downloadFile() {
        boolean flag = false;
        try {
            System.out.println("开始连接FTP服务器");
            initFtpClient();
            System.out.println("开始下载文件");
          /*  String remoteDir = "111/";
            //切换FTP目录
            ftpClient.changeWorkingDirectory(remoteDir);
            // 设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //设置为二进制传输
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            System.out.println("获取文件");
            FTPFile[] ftpFiles = ftpClient.listFiles();

            InputStream in = null;
            BufferedReader reader = null;
            for (FTPFile file : ftpFiles) {
                //判断为txt文件则解析
                if(file.isFile()){
                    String fileName = file.getName();
                    if(fileName.endsWith(".txt")){
                        in = ftpClient.retrieveFileStream(new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
                        reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        String temp;
                        StringBuffer buffer = new StringBuffer();
                        while((temp = reader.readLine())!=null){
                            buffer.append(temp);
                        }
                        if(reader!=null){
                            reader.close();
                        }
                        if(in!=null){
                            in.close();
                        }
                        //ftp.retrieveFileStream使用了流，需要释放一下，不然会返回空指针
                        ftpClient.completePendingCommand();
                        //这里就把一个txt文件完整解析成了个字符串，就可以调用实际需要操作的方法
                        System.out.println(buffer.toString());
                    }
                }
                int type = file.getType();
                Calendar timestamp = file.getTimestamp();
                Date time = timestamp.getTime();
                String nowDate = DateUtils.getNowDate(time);
                String name = file.getName();
                System.out.println(name + " --- " + nowDate+"  "+type);


                //获取待读文件输入流
//                InputStream inputStream = ftpClient.retrieveFileStream(name);
//                byte[] bytes = toByteArray(inputStream);
//                HdfsUtil.createFile("/test/aaa", bytes, name);


               *//* OutputStream outputStream = new FileOutputStream("D:\\ftp\\1111.txt");
                // 下载文件
                boolean b = ftpClient.retrieveFile(name, outputStream);
                if (b) {
                    System.out.println("下载成功");
                } else {
                    System.out.println("下载失败");
                }*//*

            }
            ftpClient.logout();
            flag = true;*/
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void initFtpClient() {
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:" + hostname + ":" + port);
            //连接ftp服务器
            ftpClient.connect(hostname, port);
            //登录ftp服务器
            ftpClient.login(username, password);
            //是否成功登录服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect failed...ftp服务器:" + hostname + ":" + port);
            }
            System.out.println("connect successfu...ftp服务器:" + hostname + ":" + port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
