package org.dandelion.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

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


    public static void main(String[] args) {
        generateUniqueLong();

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


class Time {
    private String beginTime;
    private String endTIme;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(String endTIme) {
        this.endTIme = endTIme;
    }
}

class Test {


//    public static void main(String[] args) throws ParseException {
//        List<Time> list = new ArrayList<>();
//        Time time = new Time();
//        time.setBeginTime("10:00");
//        time.setEndTIme("11:00");
//
//        Time time1 = new Time();
//        time1.setBeginTime("09:00");
//        time1.setEndTIme("13:00");
//        list.add(time);
//        list.add(time1);
//
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//
//        for (int i = 0; i < list.size(); i++) {
//            Time time2 = list.get(i);
//            String beginTime = time2.getBeginTime();
//            String endTIme = time2.getEndTIme();
//
//            Date parse1 = simpleDateFormat.parse(beginTime);
//            Date parse2 = simpleDateFormat.parse(endTIme);
//
//
//            for (int j = i + 1; j < list.size(); j++) {
//                Time time3 = list.get(j);
//                String beginTime1 = time3.getBeginTime();
//                String endTIme1 = time3.getEndTIme();
//
//                Date parse3 = simpleDateFormat.parse(beginTime1);
//                Date parse4 = simpleDateFormat.parse(endTIme1);
//
//                if (!parse3.before(parse1)) {
//                    if(!parse4.after(parse2)){
//                        System.out.println(true);
//                        return;
//                    }
//                }else if (!parse4.before(parse1)){
//                    System.out.println(true);
//                    return;
//                }
//            }
//        }
//        System.out.println(false);
//
//    }

    public static void main(String[] args) {
               /* ("1441147664255","898604B41022D0444255","1","49"),
                ("1441147664254","898604B41022D0444254","2","49"),
                ("1441147664253","898604B41022D0444253","3","49"),
                ("1441147664252","898604B41022D0444252","4","49"),
                ("1441147664251","898604B41022D0444251","5","49"),
                ("1441147664250","898604B41022D0444250","6","49"),
                ("1441147664249","898604B41022D0444249","7","49"),
                ("1441147664248","898604B41022D0444248","8","49"),
                ("1441147664247","898604B41022D0444247","9","49"),
                ("1441147664246","898604B41022D0444246","10","49"),
                ("1441147664245","898604B41022D0444245","11","49"),
                ("1441147664244","898604B41022D0444244","12","49"),
                ("1441147664243","898604B41022D0444243","13","49"),
                ("1441147664242","898604B41022D0444242","14","49"),
                ("1441147664241","898604B41022D0444241","15","49"),
                ("1441147664240","898604B41022D0444240","16","49"),
                ("1441147664239","898604B41022D0444239","17","49"),
                ("1441147664238","898604B41022D0444238","18","49"),
                ("1441147664237","898604B41022D0444237","19","49"),
                ("1441147664236","898604B41022D0444236","20","49"),
                ("1441147664235","898604B41022D0444235","21","49"),
                ("1441147664234","898604B41022D0444234","22","49"),
                ("1441147664233","898604B41022D0444233","23","49"),
                ("1441147664232","898604B41022D0444232","24","49"),
                ("1441147664231","898604B41022D0444231","25","49"),
                ("1441147664230","898604B41022D0444230","26","49"),
                ("1441147664229","898604B41022D0444229","27","49"),
                ("1441147664228","898604B41022D0444228","28","49"),
                ("1441147664227","898604B41022D0444227","29","49"),
                ("1441147664226","898604B41022D0444226","30","49"),
                ("1441147664225","898604B41022D0444225","31","49"),
                ("1441147664224","898604B41022D0444224","32","49"),
                ("1441147664223","898604B41022D0444223","33","49"),
                ("1441147664222","898604B41022D0444222","34","49"),
                ("1441147664221","898604B41022D0444221","35","49"),
                ("1441147664220","898604B41022D0444220","36","49"),
                ("1441147664219","898604B41022D0444219","37","49"),
                ("1441147664218","898604B41022D0444218","38","49"),
                ("1441147664217","898604B41022D0444217","39","49"),
                ("1441147664216","898604B41022D0444216","40","49"),


                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",

        ("1","1","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("2","2","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("3","3","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("4","4","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("5","5","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("6","6","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("7","7","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("8","8","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("9","9","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("10","10","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("11","11","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("12","12","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("13","13","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("14","14","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("15","15","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("16","16","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("17","17","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("18","18","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("19","19","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("20","20","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("21","21","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("22","22","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("23","23","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("24","24","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("25","25","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("26","26","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("27","27","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("28","28","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("29","29","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("30","30","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("31","31","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("32","32","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("33","33","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("34","34","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("35","35","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("36","36","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("37","37","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("38","38","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("39","39","2023-01-01 00:00:00","2023-02-01 00:00:00"),
        ("40","40","2023-01-01 00:00:00","2023-02-01 00:00:00"),*/


//        ("1441149280534","898604B41022C0180534","3081448560932248","49"),
//        ("1441149280627","898604B41022C0180627","3081448560932247","49"),
//        ("1441149283317","898604B41022C0183317","3081448560932246","49"),
//        ("1441147701950","898604B41022D0481950","3081448560932245","49")
    }

    /**
     * 反转字符串（循环交换）
     * 其他字符串反转的方法
     * 1、java的api：StringBuffer的reverse方法
     * 2、利用栈的特性（先进后出）
     * 3、反向遍历字符串
     * @param from
     * @return
     */
    /*public static String reChange(String from){
        char[] froms = from.toCharArray();
        int length = froms.length;
        for (int i = 0; i < length/2; i++){
            char temp = froms[i];
            froms[i] = froms[length - 1 -i];
            froms[length - 1 -i] = temp;
        }
        return String.valueOf(froms);
    }*/

    /**
     * 循环左移index位字符串
     * 思想：先部分反转，后整体反转
     * @param from
     * @param index
     * @return
     */
  /*  public static String leftMoveIndex(String from,int index){
        if(index>from.length()){
            return null;
        }
        String first = from.substring(0,index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        from = reChange(from);
        return from;
    }*/

   /* public static void main(String[] args) {
        String leftString = "abcdefg";
        System.out.println("左移2位字符串结果："+leftMoveIndex(leftString,10));
        String rightString = "abcdefg";
        System.out.println("右移2位字符串结果："+rightMoveIndex(rightString,2));

    }*/

    /**
     * 循环右移index位字符串
     * 思想：先整体反转，后部分反转
     * @param from
     * @param index
     * @return
     */
    /*public static String rightMoveIndex(String from,int index){
        from = reChange(from);
        String first = from.substring(0,index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        return from;
    }*/



}
