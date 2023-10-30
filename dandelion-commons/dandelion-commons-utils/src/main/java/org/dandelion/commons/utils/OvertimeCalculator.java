package org.dandelion.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 计算加班时长(分钟)
 *
 * @author lx6x
 * @date 2023/10/24
 */
public class OvertimeCalculator {
    private final Calendar workStartTime;
    private final Calendar workEndTime;

    public OvertimeCalculator(String workStartTime, String workEndTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.workStartTime = Calendar.getInstance();
        this.workStartTime.setTime(sdf.parse(workStartTime));
        this.workEndTime = Calendar.getInstance();
        this.workEndTime.setTime(sdf.parse(workEndTime));
    }

    public double calculateOvertimeMinutes(String startTimeStr, String endTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startTime = sdf.parse(startTimeStr);
        Date endTime = sdf.parse(endTimeStr);

        double overtimeMinutes = 0.0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        while (calendar.getTime().before(endTime)) {
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            if (isOvertimeMinute(hourOfDay, minute)) {
                double minutesWorked = 1.0; // 每分钟算作加班1分钟
                overtimeMinutes += minutesWorked;
            }

            calendar.add(Calendar.MINUTE, 1);
        }

        return overtimeMinutes;
    }

    private boolean isOvertimeMinute(int hour, int minute) {
        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, hour);
        current.set(Calendar.MINUTE, minute);

        return !current.before(workStartTime) && !current.after(workEndTime);
    }

    public static void main(String[] args) throws ParseException {
        OvertimeCalculator calculator = new OvertimeCalculator("2023-10-27 19:00", "2023-10-27 23:59");
        String startTime = "2023-10-27 20:30";
        String endTime = "2023-10-27 22:30";

        double overtimeMinutes = calculator.calculateOvertimeMinutes(startTime, endTime);
        System.out.println("加班时长（分钟）: " + overtimeMinutes);
    }
}
