package org.dandelion.oshi.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IpUtil {
    /**
     * 获取本机 ip 地址
     *
     * @return ip
     * @throws UnknownHostException
     */
    private static String getIp() throws UnknownHostException {
        InetAddress localHost = getInetAddress();
        return localHost.toString();
    }

    /**
     * 获取 InetAddress
     *
     * @return InetAddress
     * @throws UnknownHostException
     */
    private static InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * 通过本机 ip 获取对应 mac 地址
     *
     * @return mac
     * @throws UnknownHostException
     * @throws SocketException
     */
    private static String getMac() throws UnknownHostException, SocketException {
        StringBuffer sb = new StringBuffer();
        InetAddress localHost = getInetAddress();

        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                //每2位用“-”拼接
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                //十位补0
                sb.append("0").append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        /*InetAddress inetAddress = getInetAddress();

        String hostName = inetAddress.getHostName();
        System.out.println(hostName);

        String hostAddress = inetAddress.getHostAddress();
        System.out.println(hostAddress);*/

        String localIP = getLocalIP();
        System.out.println(localIP);

    }

    /**
     * 获取本地IP地址
     *
     * @throws SocketException
     */
    public static String getLocalIP() throws UnknownHostException, SocketException {
        if (isWindowsOS()) {
            return InetAddress.getLocalHost().getHostAddress();
        } else {
            return getLinuxLocalIp();
        }
    }

    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取本地Host名称
     */
    public static String getLocalHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("获取ip地址异常");
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        System.out.println("IP:" + ip);
        return ip;
    }
}
