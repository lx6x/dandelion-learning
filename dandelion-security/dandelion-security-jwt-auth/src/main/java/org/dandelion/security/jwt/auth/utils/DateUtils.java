package org.dandelion.security.jwt.auth.utils;

import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO 时间工具类
 *
 * @author L
 * @version 1.0
 * @date 2021/8/18 14:44
 */
public class DateUtils extends DateUtil {

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
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = DateUtils.SDF2;
        String dateString = formatter.format(currentTime);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNowDate(Date date) {

        return DateUtils.SDF2.format(date);
    }

    public static Date parse(String date) {
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

    public static void main(String[] args) {



    }
}
