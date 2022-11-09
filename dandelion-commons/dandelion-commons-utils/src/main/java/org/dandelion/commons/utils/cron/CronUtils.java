package org.dandelion.commons.utils.cron;

import org.quartz.CronExpression;

/**
 * TODO cron 表达式工具类
 * 目前支持三种常用的cron表达式
 * 每天的某个时间点执行      例:12 12 12 * * ?表示每天12时12分12秒执行
 * 每周的哪几天执行         例:12 12 12 ? * 1,2,3表示每周的周1周2周3 ,12时12分12秒执行
 * 每月的哪几天执行         例:12 12 12 1,21,13 * ?表示每月的1号21号13号 12时12分12秒执行
 *
 * @author L
 * @version 1.0
 * @date 2021/12/3 14:25
 */
public class CronUtils {

    /**
     * 方法摘要：构建Cron表达式
     *
     * @param taskScheduleModel
     * @return String
     */
    public static String createCronExpression(TaskScheduleModel taskScheduleModel) {
        StringBuilder cronExp = new StringBuilder("");

        if (null == taskScheduleModel.getJobType()) {
            System.out.println("执行周期未配置");
        }

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //秒
            cronExp.append(taskScheduleModel.getSecond()).append(" ");
            //分
            cronExp.append(taskScheduleModel.getMinute()).append(" ");
            //小时
            cronExp.append(taskScheduleModel.getHour()).append(" ");

            //每天
            if (taskScheduleModel.getJobType() == 1) {
                //日
                cronExp.append("* ");
                //月
                cronExp.append("* ");
                //周
                cronExp.append("?");
            } else if (taskScheduleModel.getJobType() == 2) {
                //按每月
                //一个月中的哪几天
                Integer[] days = taskScheduleModel.getDayOfMonths();
                for (int i = 0; i < days.length; i++) {
                    if (i == 0) {
                        cronExp.append(days[i]);
                    } else {
                        cronExp.append(",").append(days[i]);
                    }
                }
                //月份
                cronExp.append(" * ");
                //周
                cronExp.append("?");
            } else if (taskScheduleModel.getJobType() == 3) {
                //按每周
                //一个月中第几天
                cronExp.append("? ");
                //月份
                cronExp.append("* ");
                //周
                Integer[] weeks = taskScheduleModel.getDayOfWeeks();
                for (int i = 0; i < weeks.length; i++) {
                    if (i == 0) {
                        cronExp.append(weeks[i]);
                    } else {
                        cronExp.append(",").append(weeks[i]);
                    }
                }

            } else if (taskScheduleModel.getJobType() == 4) {
                // 间隔
            }

        } else {
            //时或分或秒参数未配置
            System.out.println("时或分或秒参数未配置");
        }
        return cronExp.toString();
    }

    /**
     * 方法摘要：生成计划的详细描述
     *
     * @param taskScheduleModel
     * @return String
     */
    public static String createDescription(TaskScheduleModel taskScheduleModel) {
        StringBuilder description = new StringBuilder("");
        //计划执行开始时间
//      Date startTime = taskScheduleModel.getScheduleStartTime();

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //按每天
            if (taskScheduleModel.getJobType() == 1) {
                description.append("每天");
                description.append(taskScheduleModel.getHour()).append("时");
                description.append(taskScheduleModel.getMinute()).append("分");
                description.append(taskScheduleModel.getSecond()).append("秒");
                description.append("执行");
            }

            //按每周
            else if (taskScheduleModel.getJobType() == 3) {
                if (taskScheduleModel.getDayOfWeeks() != null && taskScheduleModel.getDayOfWeeks().length > 0) {
                    StringBuilder days = new StringBuilder();
                    for (int i : taskScheduleModel.getDayOfWeeks()) {
                        days.append("周").append(i);
                    }
                    description.append("每周的").append(days).append(" ");
                }
                if (null != taskScheduleModel.getSecond()
                        && null != taskScheduleModel.getMinute()
                        && null != taskScheduleModel.getHour()) {
                    description.append(",");
                    description.append(taskScheduleModel.getHour()).append("时");
                    description.append(taskScheduleModel.getMinute()).append("分");
                    description.append(taskScheduleModel.getSecond()).append("秒");
                }
                description.append("执行");
            }

            //按每月
            else if (taskScheduleModel.getJobType() == 2) {
                //选择月份
                if (taskScheduleModel.getDayOfMonths() != null && taskScheduleModel.getDayOfMonths().length > 0) {
                    StringBuilder days = new StringBuilder();
                    for (int i : taskScheduleModel.getDayOfMonths()) {
                        days.append(i).append("号");
                    }
                    description.append("每月的").append(days).append(" ");
                }
                description.append(taskScheduleModel.getHour()).append("时");
                description.append(taskScheduleModel.getMinute()).append("分");
                description.append(taskScheduleModel.getSecond()).append("秒");
                description.append("执行");
            }

        }
        return description.toString();
    }

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }


    public static void main(String[] args) {
        boolean valid = isValid("0/50 * * * * ?");
        boolean valid1 = isValid("0/50 * * * ?");
        boolean valid2 = isValid("0/ * * * ?");
        System.out.println(valid);
        System.out.println(valid1);
        System.out.println(valid2);
    }


    //参考例子
    /*public static void main(String[] args) {
        //执行时间：每天的12时12分12秒 start
        TaskScheduleModel taskScheduleModel = new TaskScheduleModel();
        //按每天
        taskScheduleModel.setJobType(1);
        //时
        Integer hour = 12;
        //分
        Integer minute = 12;
        //秒
        Integer second = 12;
        taskScheduleModel.setHour(hour);
        taskScheduleModel.setMinute(minute);
        taskScheduleModel.setSecond(second);
        String cropExp = createCronExpression(taskScheduleModel);
        System.out.println(cropExp + ":" + createDescription(taskScheduleModel));
        //执行时间：每天的12时12分12秒 end

        //每周的哪几天执行
        taskScheduleModel.setJobType(3);
        Integer[] dayOfWeeks = new Integer[3];
        dayOfWeeks[0] = 1;
        dayOfWeeks[1] = 2;
        dayOfWeeks[2] = 3;
        taskScheduleModel.setDayOfWeeks(dayOfWeeks);
        cropExp = createCronExpression(taskScheduleModel);
        System.out.println(cropExp + ":" + createDescription(taskScheduleModel));

        //每月的哪几天执行
        taskScheduleModel.setJobType(2);
        Integer[] dayOfMonths = new Integer[3];
        dayOfMonths[0] = 1;
        dayOfMonths[1] = 21;
        dayOfMonths[2] = 13;
        taskScheduleModel.setDayOfMonths(dayOfMonths);
        cropExp = createCronExpression(taskScheduleModel);
        System.out.println(cropExp + ":" + createDescription(taskScheduleModel));

    }*/
}
