package org.dandelion.scheduling.rabbitmq.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/8/18 14:44
 */
public class DateUtils extends DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SDF3 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat SDF4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     * @author L
     */
    public static Date getNowDateTime() {
        Calendar instance = Calendar.getInstance();
        return instance.getTime();
    }

    /**
     * 时间 字符格式 转成 date
     *
     * @param date    时间字符串
     * @param pattern 格式
     * @return date
     */
    public static Date parseDate(String date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat sml = new SimpleDateFormat(pattern);
        if (StringUtils.isNotBlank(date)) {
            try {
                return sml.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getNowDateTime(Date date) {

        return SDF2.format(date);
    }

    public static String getNowDate(Date date) {

        return SDF1.format(date);
    }


    public static Date parseDate(String date) {
        Date time;
        try {
            time = SDF2.parse(date);
        } catch (ParseException e) {
            try {
                time = SDF1.parse(date);
            } catch (ParseException ex) {
                try {
                    time = SDF4.parse(date);
                } catch (ParseException exc) {
                    try {
                        time = SDF3.parse(date);
                    } catch (ParseException e1) {
                        throw new RuntimeException("String 日期转 Date 错误 -> 【" + date + "】");
                    }
                }
            }
        }
        return time;
    }

    /**
     * 判断时间格式的字符串是否是指定格式
     *
     * @param date 时间
     * @param sdf  指定格式
     * @return bool
     * @author L
     */
    public static boolean judgeStringDate(String date, String sdf) {
        DateFormat formatter = new SimpleDateFormat(sdf);
        ParsePosition pos = new ParsePosition(0);
        formatter.setLenient(false);
        Date result = formatter.parse(date, pos);
        return !(pos.getIndex() == 0) && date.equals(formatter.format(result));
    }

    /**
     * 判断时间大小
     *
     * @param date1 时间 1
     * @param date2 时间 2
     * @return boolean
     * @author L
     * @date 2021/12/29
     */
    public static boolean judgeDateSize(Date date1, Date date2) {
        return date1.before(date2);
    }


//    public static void main(String[] args) {

        /*LocalDateTime localDateTime=LocalDateTime.now();


        localDateTime=localDateTime.minusHours(3);

        System.out.println(localDateTime);*/


//        Map<String, Object> map = new HashMap<>();
//        map.put("loginname", "admin");
//        map.put("password", "123456");
//        String s = JSONObject.toJSONString(map);
//        String post = HttpUtil.post("http://localhost:18083/auth/login", s);
//        JSONObject jsonresult = JSON.parseObject(post);
//        System.out.println(jsonresult.toString());

//        Date date = parseDate("2023-02-22 10:30:01", null);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        System.out.println(getNowDate(calendar.getTime()));
//        calendar.add(Calendar.MINUTE, -10);
//        System.out.println(getNowDate(calendar.getTime()));
//        calendar.add(Calendar.HOUR_OF_DAY, -36);
//        System.out.println(getNowDate(calendar.getTime()));

//        calendar.add(Calendar.DATE, -1);
//        System.out.println(getNowDate(calendar.getTime()));

//        Date date1 = parseDate("2023-02-22 11:30:01", null);
//        Date date2 = parseDate("2023-02-22 10:30:01", null);
//        Date date3 = parseDate("2023-02-23 10:30:01", null);

//        boolean effectiveDate = isEffectiveDate(date1, date2, date3);
//        System.out.println(effectiveDate);
//        System.out.println(date1.before(date2));
//    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意三个参数的时间格式要一致
     *
     * @param nowTime   判断时间
     * @param startTime 时间区间
     * @param endTime   时间区间
     * @return 在时间段内返回true，不在返回false
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    /**
     * 当前时间向前后推一周
     */
    public static void week() {
        System.out.println("========LocalDate 方式=================");
        LocalDate currentDate = LocalDate.now();
        LocalDate oneWeekLater = currentDate.plusWeeks(1);
        LocalDate oneWeekEarlier = currentDate.minusWeeks(1);

        System.out.println("Current date: " + currentDate);
        System.out.println("One week later: " + oneWeekLater);
        System.out.println("One week earlier: " + oneWeekEarlier);

        System.out.println("========Calendar 方式=================");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);

        System.out.println("Current date: " + getNowDate(calendar.getTime()));
    }

    /**
     * 当前时间向前后推一月
     */
    public static void month() {
        System.out.println("========LocalDate 方式=================");
        LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthLater = currentDate.plusMonths(1);
        LocalDate oneMonthEarlier = currentDate.minusMonths(1);

        System.out.println("Current date: " + currentDate);
        System.out.println("One month later: " + oneMonthLater);
        System.out.println("One month earlier: " + oneMonthEarlier);

        System.out.println("========Calendar 方式=================");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
//        calendar.add(Calendar.MONTH, -1);
        System.out.println("Current date: " + getNowDate(calendar.getTime()));
    }

    /**
     * 当前时间向前后推一年
     */
    public static void year() {
        System.out.println("========LocalDate 方式=================");
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearLater = currentDate.plusYears(1);
        LocalDate oneYearEarlier = currentDate.minusYears(1);


        System.out.println("Current date: " + currentDate);
        System.out.println("One year later: " + oneYearLater);
        System.out.println("One year earlier: " + oneYearEarlier);

        System.out.println("========Calendar 方式=================");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
//        calendar.add(Calendar.YEAR, -1);

        System.out.println("Current date: " + getNowDate(calendar.getTime()));
    }

    /**
     * 计算两个时间点之间的天数
     */
    public static Integer dateDiffDay() {

        return null;
    }

    /**
     * 计算两个时间差
     */
    public static long getDatePoorDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        return diff / nd;
    }


//    public static void main(String[] args) throws Exception {
////        week();
////        System.out.println("------------------------------------");
////        month();
////        System.out.println("------------------------------------");
////        year();
//
//
//        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
//        Date star = dft.parse("2020-02-03");//开始时间
//        Date endDay=dft.parse("2020-02-09");//结束时间
//        long datePoorDay = getDatePoorDay(endDay, star);
//        System.out.println(datePoorDay);
//
//    }

    /**
     * 获取指定日期是星期几<br>
     *
     * @param dt 指定时间
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        System.out.println(w);
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /*public static void main(String[] args) {
        String weekOfDate = getWeekOfDate(new Date());
        System.out.println(weekOfDate);
    }*/

    /**
     * 获取当天开始时间
     */
    public static void getNowBeginDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间，当天的开始时间(日期+时分秒)："+ todayStart.format(dtf));
    }

    /**
     * 获取当天结束时间
     */
    public static void getNowEndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("当前时间，当天的结束时间(日期+时分秒)："+ todayEnd.format(dtf));
    }

    public static void main(String[] args) {
        getNowBeginDate();
        getNowEndDate();
    }


}
