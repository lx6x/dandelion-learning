package org.dandelion.commons.utils.askforleave;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


class LeaveCalculator2 {
    /**
     * 定义常见的时间格式
     */
    private static String[] dateFormat = {
            "yyyy-MM-dd HH:mm:ss", // 0
            "yyyy/MM/dd HH:mm:ss", // 1
            "yyyy年MM月dd日HH时mm分ss秒", // 2
            "yyyy-MM-dd", // 3
            "yyyy/MM/dd", // 4
            "yy-MM-dd", // 5
            "yy/MM/dd", // 6
            "yyyy年MM月dd日", // 7
            "HH:mm:ss", // 8
            "yyyyMMddHHmmss", // 9
            "yyyyMMdd", // 10
            "yyyy.MM.dd", // 11
            "yy.MM.dd", // 12
            "MM月dd日HH时mm分", // 13
            "yyyy年MM月dd日 HH:mm:ss", // 14
            "yyyy-MM-dd HH:mm", // 15
            "yyMMdd" // 16
    };


    // 获取假期日期
    public String[] holiday1() {
        return new String[]{};
    }

    // 获取调休工作日期
    public String[] holiday2() {
        return new String[]{};
    }

    // 获取除午休的工作时间
    public String[] workTimes() {

        String[] times = new String[4];
        times[0] = ("09:00:00");
        times[1] = ("12:00:00");
        times[2] = ("13:00:00");
        times[3] = ("18:00:00");
        return times;
    }

    public static void main(String[] args) {
        LeaveCalculator2 scratch3 = new LeaveCalculator2();

        String startDate = "2023-10-30 12:00:00";
        String endDate = "2023-10-30 18:00:00";
        double v = scratch3.calculationTime(startDate, endDate);
        System.out.println(v);
    }

    public double calculationTime(String startTime, String endTime) {
        // 获取startTime和endTime之间的所有日期，去掉周六周日
        List<String> list = this.getDates(startTime, endTime);
        // 获取法定节假日
        List<String> fdList = this.holiday(1);
        // 获取调休
        List<String> txList = this.holiday(2);
        // 上班时间
        String[] workTime = this.workTime();
        String[] split = workTime[2].split(":");
        String[] split1 = workTime[1].split(":");

        //午休时间
        int wxTime = (Integer.valueOf(split[0]) - Integer.valueOf(split1[0])) * 60;
        // 删除时间区间中的所有法定节假日
        list.removeAll(fdList);
        DateFormat df = new SimpleDateFormat(dateFormat[3]);
        String st = startTime.substring(0, 10);
        String en = endTime.substring(0, 10);
        try {
            Date sts = df.parse(startTime.substring(0, 10));
            Date ens = df.parse(endTime.substring(0, 10));
            for (String s : txList) {
                Date ss = df.parse(s);
                if ((ss.before(ens) && ss.after(sts)) || ss.equals(sts) || ss.equals(ens)) {
                    // 添加时间区间中的所有调休日期
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 去重
        list = this.removal(list);
        // 开始当天上午上班时间、上午下班时间、下午上班时间、下午下班时间
        String amWorkYes = startTime.substring(0, 11) + workTime[0];
        String amWorkNo = startTime.substring(0, 11) + workTime[1];
        String pmWorkYes = startTime.substring(0, 11) + workTime[2];
        String pmWorkNo = startTime.substring(0, 11) + workTime[3];

        // 结束当天上午上班时间、上午下班时间、下午上班时间、下午下班时间
        String amWorkYesEnd = endTime.substring(0, 11) + workTime[0];
        String amWorkNoEnd = endTime.substring(0, 11) + workTime[1];
        String pmWorkYesEnd = endTime.substring(0, 11) + workTime[2];
        String pmWorkNoEnd = endTime.substring(0, 11) + workTime[3];

        double time = 0;

        if (list.size() == 0) {
            // 申请日期是法定节假日
            return time;
        } else if (list.size() == 1) {
            // 请假一天
            if (startTime.compareTo(pmWorkNo) > 0) {
                return time;
            }
            if (endTime.compareTo(amWorkYes) < 0) {
                return time;
            }
            if (startTime.compareTo(amWorkNo) >= 0 && endTime.compareTo(pmWorkYes) <= 0) {
                return time;
            }

            // 开始时间小于上午上班时间，开始时间等于上午上班时间
            if (startTime.compareTo(amWorkYes) < 0) {
                startTime = amWorkYes;
            }

            // 结束时间大于下午下班时间，结束时间等于下午下班时间
            if (endTime.compareTo(pmWorkNo) > 0) {
                endTime = pmWorkNo;
            }

            // 开始时间大于上午下班时间，小于下午上班时间，开始时间等于下午上班时间
            if (startTime.compareTo(amWorkNo) >= 0 && startTime.compareTo(pmWorkYes) <= 0) {
                startTime = pmWorkYes;
            }

            // 结束时间大于上午下班时间，小于下午上班时间，结束时间等于上午下班时间
            if (endTime.compareTo(amWorkNo) >= 0 && endTime.compareTo(pmWorkYes) <= 0) {
                endTime = amWorkNo;
            }
            Date start = this.StringToDate(startTime, 15); // 0或者15
            Date end = this.StringToDate(endTime, 15);

            // 三种情况，1：请假时间全在上午，2：请假时间全在下午，3：包含午休时间
            if (startTime.compareTo(amWorkYes) >= 0 && endTime.compareTo(amWorkNo) <= 0) {
                double minute = (end.getTime() - start.getTime()) / (1000 * 60);
                time = minute / (8 * 60);
            } else if (startTime.compareTo(pmWorkYes) >= 0 && endTime.compareTo(pmWorkNo) <= 0) {
                double minute = (end.getTime() - start.getTime()) / (1000 * 60);
                time = minute / (8 * 60);
            } else if (startTime.compareTo(amWorkNo) < 0 && endTime.compareTo(pmWorkYes) > 0) {
                double minute = (end.getTime() - start.getTime()) / (1000 * 60) - wxTime;
                time = minute / (8 * 60);
            }
            return time;
        } else {
            // 处理请假多天的情况
            // 申请开始时间处理
            if (list.contains(st)) {

                if (startTime.compareTo(amWorkYes) < 0) {
                    startTime = amWorkYes;
                }
                if (startTime.compareTo(pmWorkNo) > 0) {
                    startTime = pmWorkNo;
                }
                if (startTime.compareTo(amWorkNo) >= 0 && startTime.compareTo(pmWorkYes) <= 0) {
                    startTime = pmWorkYes;
                }
                Date start = this.StringToDate(startTime, 15); // 0或者15
                Date end = this.StringToDate(pmWorkNo, 15);
                if (startTime.compareTo(amWorkNo) < 0) {
                    // 减去中午一小时
                    double t = (end.getTime() - start.getTime()) / (1000 * 60) - wxTime;
                    time = time + t / (8 * 60);
                } else {
                    double t = (end.getTime() - start.getTime()) / (1000 * 60);
                    time = time + t / (8 * 60);
                }
                list.remove(st);
            }
            // 申请结束时间处理
            if (list.contains(en)) {
                if (endTime.compareTo(amWorkYesEnd) < 0) {
                    endTime = amWorkYesEnd;
                }
                if (endTime.compareTo(pmWorkNoEnd) > 0) {
                    endTime = pmWorkNoEnd;
                }

                if (endTime.compareTo(amWorkNoEnd) >= 0 && endTime.compareTo(pmWorkYesEnd) <= 0) {
                    endTime = amWorkNoEnd;
                }

                Date end = this.StringToDate(endTime, 15);// 0或者15
                Date start = this.StringToDate(amWorkYesEnd, 15);
                if (endTime.compareTo(pmWorkYesEnd) > 0) {
                    double t = (end.getTime() - start.getTime()) / (1000 * 60) - wxTime;
                    time = time + t / (8 * 60);
                } else {
                    double t = (end.getTime() - start.getTime()) / (1000 * 60);
                    time = time + t / (8 * 60);
                }
                list.remove(en);
            }
            // 天数计算集合中剩下的个数就可以
            time = time + list.size();
            return time;
        }
    }

    /**
     * 去重
     *
     * @param str
     */

    public List<String> removal(List<String> str) {
        Set<String> s = new HashSet<String>(str);
        str.clear();
        str.addAll(s);
        return str;
    }

    /**
     * 获取两个日期之间的所有日期，去掉周末
     *
     * @param startDate
     * @param endDate
     */

    public List<String> getDates(String startDate, String endDate) {
        List<String> result = new ArrayList<String>();
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();

        startDay.setTime(StringToDate(startDate, 3));
        endDay.setTime(StringToDate(endDate, 3));

        while (startDay.before(endDay)) {
            int week = startDay.get(Calendar.DAY_OF_WEEK);
            if (7 != week && 1 != week) {
                result.add(dateToString(startDay.getTime(), 3));
            }
            startDay.add(Calendar.DAY_OF_YEAR, 1);
        }
        // 验证结束日期是否是周六周日
        int week = endDay.get(Calendar.DAY_OF_WEEK);
        if (7 != week && 1 != week) {
            result.add(dateToString(endDay.getTime(), 3));
        }

        return result;
    }

    /**
     * 字符串转时间
     *
     * @param dateStr
     * @param index
     */

    public Date StringToDate(String dateStr, int index) {
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(dateFormat[index]);
            return df.parse(dateStr);
        } catch (Exception aioe) {
            return null;
        }
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @param index
     */

    public String dateToString(Date date, int index) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(dateFormat[index]).format(date);
    }

    /**
     * 获取法定节假日或者调休
     *
     * @param num
     */

    public List<String> holiday(int num) {
        if (num == 2) {
            return Arrays.asList(this.holiday2());
        } else {
            return Arrays.asList(this.holiday1());
        }
    }

    /**
     * 获取不同部门工作时间
     */

    public String[] workTime() {
        return this.workTimes();
    }


    public double halfDayMath(double time) {
        String timeStr = time + "";
        System.out.println("timeStr = [" + timeStr + "]");

        String[] timeArr = timeStr.split("\\.");
        int t1 = Integer.valueOf(timeArr[0]);
        System.out.println("t1 = [" + t1 + "]");
        int t2 = Integer.valueOf(timeArr[1].substring(0, 1));
        System.out.println("t2 = [" + t2 + "]");

        if (t2 < 5) {
            return t1;
        } else if (t2 > 5) {
            t1++;
            return t1;
        } else {
            return t1 + 0.5f;
        }

    }
}