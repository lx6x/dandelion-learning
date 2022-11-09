package org.dandelion.netty.im.utils;

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

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * TODO http请求工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 18:19
 */
public class HttpUtils {

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
                result = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
