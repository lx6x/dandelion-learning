package org.dandelion.commons.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

/**
 * TODO json 解析
 *
 * @author L
 * @version 1.0
 * @date 2021/10/19 9:43
 */
public class JsonUtils {

    /**
     * json 本地文件解析
     *
     * @author L
     */
    private static void jsonFileParsing() {
        try {
            File file = ResourceUtils.getFile("D:\\code\\work\\idea\\dandelion-parent\\dandelion-commons\\dandelion-commons-utils\\src\\main\\resources\\json\\Test.json");
            String s = FileUtils.readFileToString(file);
            JSONObject s1 = JSONObject.parseObject(s);
            System.out.println(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        jsonFileParsing();
    }
}
