package org.dandelion.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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

    private static final Random random = new Random();

    public static long generateUniqueLong() {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);

        // 生成一个 10 位的随机数
        long randomValue = random.nextInt(1000000000);

        // 合并时间戳和随机数生成唯一的 Long 值
        long uniqueLong = timestamp * 1000000000L + randomValue;
        System.out.println(uniqueLong);
        return uniqueLong;
    }

    /**
     * 反转字符串（循环交换）
     * 其他字符串反转的方法
     * 1、java的api：StringBuffer的reverse方法
     * 2、利用栈的特性（先进后出）
     * 3、反向遍历字符串
     *
     * @param from
     * @return
     */
    public static String reChange(String from) {
        char[] froms = from.toCharArray();
        int length = froms.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = froms[i];
            froms[i] = froms[length - 1 - i];
            froms[length - 1 - i] = temp;
        }
        return String.valueOf(froms);
    }

    /**
     * 循环左移index位字符串
     * 思想：先部分反转，后整体反转
     *
     * @param from
     * @param index
     * @return
     */
    public static String leftMoveIndex(String from, int index) {
        if (index > from.length()) {
            return null;
        }
        String first = from.substring(0, index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        from = reChange(from);
        return from;
    }


    /**
     * 循环右移index位字符串
     * 思想：先整体反转，后部分反转
     *
     * @param from
     * @param index
     * @return
     */
    public static String rightMoveIndex(String from, int index) {
        from = reChange(from);
        String first = from.substring(0, index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        return from;
    }


    /**
     * 前缀中删除所有的'0'字符
     */
    public static String sub0(String s) {
        return s = s.replaceAll("^0+", ""); // 使用replaceAll替换所有前缀的'0'
    }

    /*public static void main(String[] args) {
        String leftString = "abcdefg";
        System.out.println("左移2位字符串结果："+leftMoveIndex(leftString,10));
        String rightString = "abcdefg";
        System.out.println("右移2位字符串结果："+rightMoveIndex(rightString,2));

    }*/


/*    public static void main(String[] args) {
        // 随机字符串
//        generateUniqueLong();

        // 拼接字符
        StringJoiner sj=new StringJoiner(",","(",")");
        List<String> list=new LinkedList<>();
        list.add("1");
        list.add("2");
        for (String s : list) {
            sj.add(s);
        }
        System.out.println(sj);
    }*/
}
