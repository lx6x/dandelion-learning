package org.dandelion.commons.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//        jsonFileParsing();

        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>(16);
        map1.put("a", "1");
        map1.put("b", "2");

        Map<String, String> map2 = new HashMap<>(16);
        map2.put("a", "4");
        map2.put("b", "2");

        Map<String, String> map3 = new HashMap<>(16);
        map3.put("a", "4");
        map3.put("b", "2");

        Map<String, String> map4 = new HashMap<>(16);
        map4.put("a", "4");
        map4.put("b", "5");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        List<String> a = list.stream().map(map -> map.get("a")).distinct().collect(Collectors.toList());
        long b = list.stream().map(map -> map.get("a")).distinct().count();
        System.out.println(a);
        System.out.println(b);

    }
}
