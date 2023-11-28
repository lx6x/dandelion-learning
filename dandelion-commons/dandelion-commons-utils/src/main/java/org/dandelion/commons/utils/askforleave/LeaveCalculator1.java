package org.dandelion.commons.utils.askforleave;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lx6x
 * @date 2023/10/30
 */
public class LeaveCalculator1 {

    private static final SimpleDateFormat Y_M_D_H_M_S = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat H_M_S = new SimpleDateFormat("hh:mm:ss");

    private static final String[] workTime = workTime();

    private static final List<String> rest = rest();
    private static final List<String> work = work();

    public static void main(String[] args) throws Exception {
        String start = "2023-10-19 08:00:00";
        String end = "2023-10-24 11:00:00";

//        String start = "2023-10-10 09:00:00";
//        String end = "2023-10-12 18:00:00";
        double v = calculationTime(start, end);
        System.out.println(v);

    }

    private static double calculationTime(String start, String end) throws Exception {


        String startSub = start.substring(0, 10);
        String endSub = end.substring(0, 10);

        DateTime startDate = DateUtil.parse(start);
        DateTime endDate = DateUtil.parse(end);

        System.out.println("开始时间：" + startDate);
        System.out.println("结束时间：" + endDate);

        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);

        List<String> dayList = getDate(startDateCal, endDateCal);
        System.out.println("请假天：" + dayList);

        // 天数
        double day = 0.0;

        // 开始当天上午上班时间、上午下班时间、下午上班时间、下午下班时间
        String amWorkYes = start.substring(0, 11) + workTime[0]; // 9:00
        String amWorkNo = start.substring(0, 11) + workTime[1];  // 12:00
        String pmWorkYes = start.substring(0, 11) + workTime[2]; // 13:00
        String pmWorkNo = start.substring(0, 11) + workTime[3];  // 18:00

        // 结束当天上午上班时间、上午下班时间、下午上班时间、下午下班时间
        String amWorkYesEnd = end.substring(0, 11) + workTime[0];   // 9:00
        String amWorkNoEnd = end.substring(0, 11) + workTime[1];    // 12:00
        String pmWorkYesEnd = end.substring(0, 11) + workTime[2];   // 13:00
        String pmWorkNoEnd = end.substring(0, 11) + workTime[3];    // 18:00

        if (dayList.size() == 1) {

            // 判断是否只有一天

            // 开始时间小于上午上班时间，开始时间等于上午上班时间
            if (start.compareTo(amWorkYes) < 0) {
                start = amWorkYes;
            }

            // 结束时间大于下午下班时间，结束时间等于下午下班时间
            if (end.compareTo(pmWorkNo) > 0) {
                end = pmWorkNo;
            }

            // 开始时间大于上午下班时间，小于下午上班时间，开始时间等于下午上班时间
            if (start.compareTo(amWorkNo) >= 0 && start.compareTo(pmWorkYes) <= 0) {
                start = pmWorkYes;
            }

            // 结束时间大于上午下班时间，小于下午上班时间，结束时间等于上午下班时间
            if (end.compareTo(amWorkNo) >= 0 && end.compareTo(pmWorkYes) <= 0) {
                end = amWorkNo;
            }

            // 三种情况，1：请假时间全在上午，2：请假时间全在下午，3：包含午休时间
            if (start.compareTo(amWorkYes) >= 0 && end.compareTo(amWorkNo) <= 0) {
                day = day + 0.5;
            } else if (start.compareTo(pmWorkYes) >= 0 && end.compareTo(pmWorkNo) <= 0) {
                day = day + 0.5;
            } else if (start.compareTo(amWorkNo) < 0 && end.compareTo(pmWorkYes) > 0) {
                day = day + 1;
            }

        } else {
            for (String s : dayList) {

                if (s.equals(startSub)) {
                    // 判断是否是第一天

                    // 如果当天是第一天，那请假最大时长为当天的六点
                    if (start.compareTo(amWorkYes) < 0) {
                        start = amWorkYes;
                    }
                    if (start.compareTo(pmWorkNo) > 0) {
                        start = pmWorkNo;
                    }
                    if (start.compareTo(amWorkNo) >= 0 && start.compareTo(pmWorkYes) <= 0) {
                        start = pmWorkYes;
                    }

                    if (start.compareTo(amWorkNo) < 0) {
                        day = day + 1;
                    } else {
                        day = day + 0.5;
                    }
                } else if (s.equals(endSub)) {
                    // 判断是否是最后一天

                    // 如果当天是最后一天，那请假的最大时长为传入时间的截止时间

                    if (end.compareTo(amWorkYesEnd) < 0) {
                        end = amWorkYesEnd;
                    }
                    if (end.compareTo(pmWorkNoEnd) > 0) {
                        end = pmWorkNoEnd;
                    }

                    if (end.compareTo(amWorkNoEnd) >= 0 && end.compareTo(pmWorkYesEnd) <= 0) {
                        end = amWorkNoEnd;
                    }

                    if (end.compareTo(pmWorkYesEnd) > 0) {
                        day = day + 1;
                    } else {
                        day = day + 0.5;
                    }

                } else {
                    // 其余都按照一天算
                    day++;
                }
            }
        }


        return day;
    }

    /**
     * 获取除去节假日/周六日 其余的时间
     *
     * @param startDateCal 开始时间
     * @param endDateCal   结束时间
     */
    private static List<String> getDate(Calendar startDateCal, Calendar endDateCal) {
        List<String> dayList = new ArrayList<>();

        // 当前时间在结束时间之前进入循环
        while (Y_M_D.format(startDateCal.getTime()).compareTo(Y_M_D.format(endDateCal.getTime())) <= 0) {
            String startDateCalFormat = Y_M_D.format(startDateCal.getTime());

            // ------------------------------------------------------
            // 判断是否是周六日 周日-1 ...  周六-7
            int dayOfWeek = startDateCal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                System.out.println("周末：" + startDateCalFormat);
                if (work.contains(startDateCalFormat)) {
                    System.out.println("周末-调休：" + startDateCalFormat);
                    // 周六日是调休日
                    dayList.add(startDateCalFormat);
                }
                startDateCal.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }
            // ------------------------------------------------------

            // ------------------------------------------------------
            boolean contains = rest.contains(startDateCalFormat);
            // 判断是否是节假日
            if (contains) {
                System.out.println("节假日：" + startDateCalFormat);
                startDateCal.add(Calendar.DAY_OF_MONTH, 1);
                continue;
            }
            // ------------------------------------------------------

            // 格式化天
            dayList.add(startDateCalFormat);

            // 每次循环开始时间加一天
            startDateCal.add(Calendar.DAY_OF_MONTH, +1);
        }
        return dayList;
    }

    /**
     * 节假日
     */
    public static List<String> rest() {
        List<String> list = new LinkedList<>();
        list.add("2023-10-10");
        list.add("2023-10-12");
        list.add("2023-10-20");
//        list.add("2023-10-21");
//        list.add("2023-10-22");
//        list.add("2023-10-23");
//        list.add("2023-10-24");
//        list.add("2023-10-25");
        return list;
    }

    /**
     * 调休
     */
    public static List<String> work() {
        List<String> list = new LinkedList<>();
        list.add("2023-10-22");
        return list;
    }

    public static String[] workTime() {
        String[] dayTime = new String[4];
        dayTime[0] = ("09:00:00");
        dayTime[1] = ("12:00:00");
        dayTime[2] = ("13:00:00");
        dayTime[3] = ("18:00:00");
        return dayTime;
    }
}
