package org.dandelion.oshi.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

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

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = getInetAddress();

        String hostName = inetAddress.getHostName();
        System.out.println(hostName);

        String hostAddress = inetAddress.getHostAddress();
        System.out.println(hostAddress);


    }
}
