package org.dandelion.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO jsch 操作类
 *      说明：exec用于执行命令;sftp用于文件处理
 *
 * @author L
 * @version 1.0
 * @date 2022/2/16 10:23
 */
public class JschUtils {

    private static final Logger logger = LoggerFactory.getLogger(JschUtils.class);

    private static JschUtils jschUtils;
    private Session session;
    private String ipAddress;
    private static final int DEFAULT_PORT = 22;
    private int port;
    private String userName;
    private String password;
    private boolean login = false;
/*
    private JschUtils() {

    }

    public static synchronized JschUtils getInstance() {
        if (null == jschUtils) {
            jschUtils = new JschUtils();
        }
        return jschUtils;
    }

    public static synchronized JschUtils getInstance(String ipAddress, int port, String userName, String password) {
        if (null == jschUtils) {
            jschUtils = new JschUtils(ipAddress, port, userName, password);
        }
        return jschUtils;
    }*/

    public JschUtils(String ipAddress, String userName, String password) {
        this(ipAddress, DEFAULT_PORT, userName, password);
    }

    public JschUtils(String ipAddress, int port, String userName, String password) {
        super();
        this.ipAddress = ipAddress;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }


    /**
     * 远程登录
     *
     * @author L
     */
    private void sshRemoteCallLogin() throws Exception {
        JSch jSch = new JSch();
        try {
            // 获取 session，根据用户名，主机地址，端口
            this.session = jSch.getSession(this.userName, this.ipAddress, this.port);
            // 设置密码
            this.session.setPassword(this.password);

            // 方式一,通过Session建立连接
            // session.setConfig("StrictHostKeyChecking", "no");
            // session.connect();

            // 方式二,通过Session建立连接
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            this.session.setConfig(config);
            // 设置超时
            this.session.setTimeout(3000);
            // 通过Session建立连接
            this.session.connect();

            this.login = true;
        } catch (JSchException e) {
            this.login = false;
            throw new Exception("主机登录失败, IP = " + this.ipAddress + ", USERNAME = " + this.userName + ", Exception:" + e.getMessage());
        }
    }

    /**
     * 命令执行 exec
     * <p>
     * 对于ChannelExec,在调用connect()方法之前这个命令提供了setCommand()方法，并且这些命令作为输入将以输入流的形式被发送出去。（通常，你只能有调用setCommand()方法一次，多次调用只有最后一次生效），但是你可以使用普通shell的分隔符来提供多个命令。这就像在你本机上执行一个shell脚本一样（当然，如果一个命令本身就是个交互式shell，这样就像ChannelShell）。
     * <p>
     * ChannelExec复合命令：
     * 每个命令之间用 ; 隔开。说明：各命令的执行给果，不会影响其它命令的执行。换句话说，各个命令都会执行，但不保证每个命令都执行成功。
     * 每个命令之间用 && 隔开。说明：若前面的命令执行成功，才会去执行后面的命令。这样可以保证所有的命令执行完毕后，执行过程都是成功的。
     * 每个命令之间用 || 隔开。说明：|| 是或的意思，只有前面的命令执行失败后才去执行下一条命令，直到执行成功一条命令为止。
     *
     * @param command 命令
     * @author L
     */
    public String execCommandExec(String command) {
        StringBuffer sb = new StringBuffer();

        if (login) {
            if (command == null) {
                logger.error("{} ssh command is null", this.ipAddress);
                return sb.toString();
            }

            InputStream inputStream;
            ChannelExec channel;

            try {
                // 打开channel
                //说明：exec用于执行命令;sftp用于文件处理
                channel = (ChannelExec) session.openChannel("exec");
                // 设置command
                channel.setCommand(command);
                // channel进行连接
                channel.connect();
                // 获取到输入流
                inputStream = channel.getInputStream();
                // 执行相关的命令
                String dataStream = processDataStream(inputStream);
                // 打印相关的命令
                sb.append(dataStream).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } else {
            logger.info("{} 主机登录失败", this.ipAddress);
        }
        return sb.toString();
    }

    /**
     * 命令执行 shell
     * ChannelShell
     * 对于ChannelShell，以输入流的形式，可执行多条指令，这就像在本地计算机上使用交互式shell（它通常用于：交互式使用）。如要要想停止，有两种方式：
     * <p>
     * 发送一个exit命令，告诉程序本次交互结束；
     * 使用字节流中的available方法，来获取数据的总大小，然后循环去读。
     *
     * @param command 命令
     * @author L
     */
    public String execCommandShell(String[] command) {
        StringBuilder sb = new StringBuilder();

        if (login) {
            if (command == null || command.length == 0) {
                logger.error("{} ssh command is null", this.ipAddress);
                return sb.toString();
            }

            InputStream inputStream;
            ChannelShell channel;

            try {
                // 打开channel
                //说明：exec用于执行命令;sftp用于文件处理
                channel = (ChannelShell) session.openChannel("shell");
                // channel进行连接
                channel.connect();
                // 设置command
                sendCommands(channel, command);
                inputStream = channel.getInputStream();
                // 结果返回
                String dataStream = processDataStream(inputStream);
                // 打印相关的命令
                sb.append(dataStream).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } else {
            logger.info("{} 主机登录失败", this.ipAddress);
        }
        return sb.toString();
    }


    /**
     * 发送Shell命令
     *
     * @param channel  c
     * @param commands shell命令集
     * @author L
     */
    private void sendCommands(Channel channel, String... commands) {
        try {
            PrintStream out = new PrintStream(channel.getOutputStream());
            for (String command : commands) {
                out.println(command);
            }
            out.println("exit");// 加上个就是为了，结束本次交互
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对将要执行的linux的命令进行遍历
     *
     * @param in 流
     * @return string
     */
    public String processDataStream(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String result = "";
            while ((result = br.readLine()) != null) {
                System.out.println(
                        result
                );
                sb.append(result).append("\n");
            }
        } catch (Exception e) {
            throw new Exception("获取数据流失败: " + e);
        }
        return sb.toString();
    }


    /**
     * 上传文件
     *
     * <p>ChannelSftp.OVERWRITE：完全覆盖模式，这是JSch的默认文件传输模式，即如果目标文件已经存在，传输的文件将完全覆盖目标文件，产生新的文件。</p>
     * <p>ChannelSftp.RESUME：恢复模式，如果文件已经传输一部分，这时由于网络或其他任何原因导致文件传输中断，如果下一次传输相同的文件，则会从上一次中断的地方续传。</p>
     *
     * @param count      文件内容
     * @param uploadFile 上传全路径（加文件名称）
     * @author L
     */
    public void uploadFile(String count, String uploadFile) {
        try {
            // 打开channelSftp
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // 远程连接
            channelSftp.connect();
            // 创建一个文件名称问uploadFile的文件
            // 将文件进行上传(sftp协议)
            byte[] bytes = count.getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            // 采用默认的传输模式:OVERWRITE
            channelSftp.put(inputStream, uploadFile, ChannelSftp.OVERWRITE);
            // 切断远程连接
            channelSftp.exit();
            System.out.println(" 文件上传成功.....");
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param directoryFile 要删除文件所在全路径
     * @author L
     */
    public void deleteFile(String directoryFile) throws SftpException, JSchException {
        // 打开openChannel的sftp
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 删除文件
        channelSftp.rm(directoryFile);
        // 切断远程连接
        channelSftp.exit();
        System.out.println("4、" + directoryFile + " 删除的文件.....");
    }

    /**
     * 关闭连接
     *
     * @author L
     */
    public void closeSession() {
        // 调用session的关闭连接的方法
        if (session != null) {
            // 如果session不为空,调用session的关闭连接的方法
            session.disconnect();
        }
    }

    public static void main(String[] args) {
        JschUtils instance = new JschUtils("192.168.80.100", "root", "root");
        try {
            // 登录
            instance.sshRemoteCallLogin();

            if (instance.login) {

//                String[] strings = {"cd /root", "cat anaconda-ks.cfg"};
                String[] strings = {"java -cp  /opt/flinkx/lib/flinkx-clients-master.jar  com.dtstack.flinkx.client.Launcher -mode local -jobType sync -job /opt/flinkx/flinkx-examples/json/postgresql/test_without_numeric.json -flinkxDistDir /opt/flinkx/flinkx-dist"};
                String s = instance.execCommandShell(strings);
                System.out.println(s);


//                Map<String, String> map = new HashMap<>(16);
//                map.put("a", "1");
//                map.put("b", "2");
//
//                instance.uploadFile(JSONObject.toJSONString(map), "/root/test.json");

//                instance.deleteFile("/root/test.json");
            }

            instance.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
