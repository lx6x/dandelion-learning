package org.dandelion.commons.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO 字符处理工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/11/11 17:48
 */
public class StringUtil {

    /**
     * 字符转换二进制
     *
     * @param str 字符串
     */
    public static String stringToBinary(String str) {
        char[] strChar = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : strChar) {
            result.append(Integer.toBinaryString(c)).append(" ");
        }
        return result.toString();
    }

    public static char binaryToChar(String binStr) {
        int[] temp = binaryToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    public static int[] binaryToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    /**
     * 二进制转字符
     *
     * @param binary 二进制串
     */
    public static String binaryToStr(String binary) {
        String[] tempStr = binary.split(" ");
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = binaryToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    /**
     * InputStream 转 String
     *
     * @param is   .
     * @param code 编码
     * @return string
     */
    public static String inputStreamToStringByCode(InputStream is, String code) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, code));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    /**
     * 判断数值是否在一个区间
     *
     * @param current 数值
     * @param min     区间最小值
     * @param max     区间最大值
     * @return bool
     * @author L
     */
    public static boolean rangeInDefined(int current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }


    /**
     * 获取匹配字符最后一次出现位置的下标
     *
     * @param s  字符串
     * @param s1 所要匹配字符
     * @return java.lang.Integer
     * @author L
     * @date 2021/12/11
     */
    public static Integer searchString(String s, String s1) {
        return s.lastIndexOf(s1);
    }

   /* public static void main(String[] args) {
        String[] s1 = {"1", "2"};
        String[] s2 = {"3", "4"};
        String[] s3 = {"5", "6"};
        String[] s4 = {"7", "8"};
        String[] s5 = {"9", "10"};
        String[] s6 = {"11", "12"};
        List<String[]> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        System.err.println(JSONObject.toJSONString(list));

        int size = list.size();
        for (int i = 0; i < list.size(); i++) {

            String[] strings1;
            String[] strings2;
            if (i == size-1) {
                strings1 = list.get(i);
                strings2 = list.get(0);
            } else {
                strings1 = list.get(i);
                strings2 = list.get(i + 1);
            }
            System.out.println(JSONObject.toJSONString(strings1) + "   " + JSONObject.toJSONString(strings2) + "  " + i);


        }


        System.out.println("-----------------------------");

        System.out.println(UUID.randomUUID());
    }*/

    public static void main(String[] args) {
//        int sum;
//        int i,j;
//        for (i=1;i<10;i++){
//            for (j=1;j<10;j++){
//                if(j<=i){
//                    sum=i*j;
//                    System.out.println(i +" "+j+" = " + sum);
//                }
//            }
//            System.out.println("");
//        }

       /* for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "x" + i + "=" + (j * i) + "\t");
            }
            System.out.println();
        }*/

//        List<String> list=new ArrayList<>();
//        list.add("1");

//        list=list.stream().limit(5).collect(Collectors.toList());
//
        // 倒叙排
//        pieByTypeList = pieByTypeList.stream().sorted(Comparator.comparing(AccidentStatisticsResp.Pie::getValue).reversed()).limit(5).collect(Collectors.toList());

//        System.out.println(list);

    }
}
