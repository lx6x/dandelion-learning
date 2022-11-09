package org.dandelion.repeat.submit.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 客户端工具类
 *
 * @author L
 * @version 1.0
 * @date 2022/06/15 10:50
 */
public class ServletUtils {

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
