package org.dandelion.commons.utils.askforleave;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujunfei
 * @date 2023/10/30
 */
public class LeaveCalculator {
    private static final List<LocalDate> HOLIDAYS = new ArrayList<>();

    static {
        // 添加节假日
        HOLIDAYS.add(LocalDate.of(2019, 1, 1)); // 元旦
        HOLIDAYS.add(LocalDate.of(2019, 2, 4)); // 春节
        HOLIDAYS.add(LocalDate.of(2019, 2, 5));
        HOLIDAYS.add(LocalDate.of(2019, 2, 6));
        HOLIDAYS.add(LocalDate.of(2019, 2, 7));
        HOLIDAYS.add(LocalDate.of(2019, 2, 8));
        HOLIDAYS.add(LocalDate.of(2019, 4, 5)); // 清明节
        HOLIDAYS.add(LocalDate.of(2019, 5, 1)); // 劳动节
        HOLIDAYS.add(LocalDate.of(2019, 6, 7)); // 端午节
        HOLIDAYS.add(LocalDate.of(2019, 9, 13)); // 中秋节
        HOLIDAYS.add(LocalDate.of(2019, 10, 1)); // 国庆节
        HOLIDAYS.add(LocalDate.of(2019, 10, 2));
        HOLIDAYS.add(LocalDate.of(2019, 10, 3));
        HOLIDAYS.add(LocalDate.of(2019, 10, 4));
        HOLIDAYS.add(LocalDate.of(2019, 10, 5));
        HOLIDAYS.add(LocalDate.of(2019, 10, 6));
        HOLIDAYS.add(LocalDate.of(2019, 10, 7));
        HOLIDAYS.add(LocalDate.of(2020, 1, 1)); // 元旦
        HOLIDAYS.add(LocalDate.of(2020, 1, 24)); // 春节
        HOLIDAYS.add(LocalDate.of(2020, 1, 25));
        HOLIDAYS.add(LocalDate.of(2020, 1, 26));
        HOLIDAYS.add(LocalDate.of(2020, 1, 27));
        HOLIDAYS.add(LocalDate.of(2020, 1, 28));
        HOLIDAYS.add(LocalDate.of(2020, 1, 29));
        HOLIDAYS.add(LocalDate.of(2020, 1, 30));
        HOLIDAYS.add(LocalDate.of(2020, 4, 4)); // 清明节
        HOLIDAYS.add(LocalDate.of(2020, 5, 1)); // 劳动节
        HOLIDAYS.add(LocalDate.of(2020, 5, 4));
        HOLIDAYS.add(LocalDate.of(2020, 5, 5));
        HOLIDAYS.add(LocalDate.of(2020, 6, 25)); // 端午节
        HOLIDAYS.add(LocalDate.of(2020, 10, 1)); // 国庆节
        HOLIDAYS.add(LocalDate.of(2020, 10, 2));
        HOLIDAYS.add(LocalDate.of(2020, 10, 3));
        HOLIDAYS.add(LocalDate.of(2020, 10, 4));
        HOLIDAYS.add(LocalDate.of(2020, 10, 5));
        HOLIDAYS.add(LocalDate.of(2020, 10, 6));
        HOLIDAYS.add(LocalDate.of(2020, 10, 7));
    }

    public static double calculateLeaveDays(LocalDateTime startTime, LocalDateTime endTime) {
        double leaveDays = 0.0;

        // 计算请假开始时间和结束时间的日期差
        long days = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());

        // 如果请假开始时间和结束时间在同一天
        if (days == 0) {
            // 如果请假开始时间在上午9点到中午12点之间，按0.5天计算
            if (startTime.toLocalTime().isAfter(LocalTime.of(1, 0)) && startTime.toLocalTime().isBefore(LocalTime.of(12, 0))) {
                leaveDays += 0.5;
            }
            // 如果请假结束时间在中午12点01分到晚上8点之间，按0.5天计算
            if (endTime.toLocalTime().isAfter(LocalTime.of(12, 0, 1)) && endTime.toLocalTime().isBefore(LocalTime.of(23, 59))) {
                leaveDays += 0.5;
            }
        } else {
            // 如果请假开始时间在上午9点到中午12点之间，按0.5天计算
            if (startTime.toLocalTime().isAfter(LocalTime.of(1, 0)) && startTime.toLocalTime().isBefore(LocalTime.of(12, 0))) {
                leaveDays += 0.5;
            }
            // 如果请假结束时间在中午12点01分到晚上8点之间，按0.5天计算
            if (endTime.toLocalTime().isAfter(LocalTime.of(12, 0, 1)) && endTime.toLocalTime().isBefore(LocalTime.of(23, 59))) {
                leaveDays += 0.5;
            }

            // 计算请假期间的工作日天数
            for (int i = 0; i < days; i++) {
                LocalDate date = startTime.toLocalDate().plusDays(i);
                if (!isHoliday(date)) {
                    leaveDays += 1.0;
                }
            }
        }

        return leaveDays;
    }

    private static boolean isHoliday(LocalDate date) {
        // 判断是否是周末
        if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) {

            // TODO 还需要判断是否是周六日调休
            return true;
        }

        // 判断是否是节假日
        return HOLIDAYS.contains(date);
    }

    public static void main(String[] args) {
//            LocalDateTime startTime = LocalDateTime.of(2023, 10, 30, 10, 0);
//            LocalDateTime endTime1 = LocalDateTime.of(2023, 11, 2, 10, 0);
//
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 12, 9, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2023, 10, 13, 12, 0);
        double leaveDays1 = LeaveCalculator.calculateLeaveDays(startTime, endTime1);
        System.out.println("请假时长为：" + leaveDays1);
    }
}
