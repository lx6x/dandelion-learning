package org.dandelion.commons.utils;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO http请求工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 18:19
 */
public class HttpUtils {

    private static String cookieVal = null;

    /**
     * 发送 get 请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @author L
     */
    public static String get(String url) {
        String result = null;
        CloseableHttpResponse response = null;
        CookieStore httpCookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                List<Cookie> cookies = httpCookieStore.getCookies();
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    System.out.println("cookies: key= " + name + "  value= " + value);
                }
                result = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        String post = post("http://localhost:8080/xxl-job-admin/login?userName=admin&password=123456", "{}");
        System.out.println(post);
        System.out.println("===========================");

        String post1 = post("http://localhost:8080/xxl-job-admin/jobinfo/pageList?jobGroup=1&triggerStatus=-1&start=0&length=10", "{}");
        System.out.println(post1);
    }

    /**
     * 发送 post 请求
     *
     * @param url     请求地址
     * @param jsonStr Form表单json字符串
     * @return 请求结果
     * @author L
     */
    public static String post(String url, String jsonStr) {
        // 创建httpClient
        CookieStore httpCookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore).build();
        // 创建post请求方式实例
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头 发送的是json数据格式
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        httpPost.setHeader("Cookie", cookieVal);

        // 设置参数---设置消息实体 也就是携带的数据
        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
        // 设置编码格式
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        // 把请求消息实体塞进去
        httpPost.setEntity(entity);

        // 执行http的post请求
        CloseableHttpResponse httpResponse;
        String result = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            List<Cookie> cookies = httpCookieStore.getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println("cookies: key= " + name + "  value= " + value);
                cookieVal = name + "=" + value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 获取ip
     *
     * @param request .
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
