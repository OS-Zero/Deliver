package com.oszero.deliver.admin.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * Quartz工具类
 * 提供动态操作Quartz任务的常用方法，包括任务的增删改查、启动与暂停等操作。
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
public class QuartzJobUtils {

    private static final String TRIGGER_PRE_NAME = "trigger";

    private QuartzJobUtils() {
        // 工具类不允许实例化
    }

    /**
     * 添加任务
     *
     * @param scheduler     调度器
     * @param jobName       任务名称
     * @param jobGroup      任务组名称
     * @param cronExpression Cron表达式
     * @param jobClass      执行任务的类，需实现org.quartz.Job接口
     * @throws SchedulerException 调度异常
     */
    public static void addJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression, Class<? extends Job> jobClass) throws SchedulerException {
        // 检查任务是否存在
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            log.warn("任务已存在: jobName={}, jobGroup={}", jobName, jobGroup);
            return;
        }
        // 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(jobName, jobGroup)
            .build();
        // 创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(TRIGGER_PRE_NAME + jobName, jobGroup)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("任务添加成功: jobName={}, jobGroup={}, cronExpression={}", jobName, jobGroup, cronExpression);
    }

    /**
     * 添加任务（支持传递参数）
     *
     * @param scheduler     调度器
     * @param jobName       任务名称
     * @param jobGroup      任务组名称
     * @param cronExpression Cron表达式
     * @param jobClass      执行任务的类，需实现org.quartz.Job接口
     * @param jobDataMap    任务参数，键值对形式
     * @throws SchedulerException 调度异常
     */
    public static void addJobWithParams(Scheduler scheduler, String jobName, String jobGroup, String cronExpression,
                                        Class<? extends Job> jobClass, JobDataMap jobDataMap) throws SchedulerException {
        // 检查任务是否存在
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            log.warn("任务已存在: jobName={}, jobGroup={}", jobName, jobGroup);
            return;
        }
        // 创建JobDetail并绑定参数
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(jobName, jobGroup)
            .setJobData(jobDataMap) // 设置任务参数
            .build();
        // 创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(TRIGGER_PRE_NAME + jobName, jobGroup)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("任务添加成功（带参数）: jobName={}, jobGroup={}, cronExpression={}, params={}",
            jobName, jobGroup, cronExpression, jobDataMap);
    }

    /**
     * 更新任务的Cron表达式
     *
     * @param scheduler     调度器
     * @param jobName       任务名称
     * @param jobGroup      任务组名称
     * @param newCronExpression 新的Cron表达式
     * @throws SchedulerException 调度异常
     */
    public static void updateJobCron(Scheduler scheduler, String jobName, String jobGroup, String newCronExpression) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(TRIGGER_PRE_NAME + jobName, jobGroup);
        // 检查触发器是否存在
        if (!scheduler.checkExists(triggerKey)) {
            log.warn("触发器不存在: jobName={}, jobGroup={}", jobName, jobGroup);
            return;
        }
        // 获取当前触发器
        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 如果Cron表达式未改变，则无需更新
        if (StrUtil.equals(oldTrigger.getCronExpression(), newCronExpression)) {
            log.info("Cron表达式未更改: {}", newCronExpression);
            return;
        }
        // 创建新的Trigger
        Trigger newTrigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(newCronExpression))
            .build();
        // 更新触发器
        scheduler.rescheduleJob(triggerKey, newTrigger);
        log.info("任务Cron表达式更新成功: jobName={}, jobGroup={}, newCronExpression={}", jobName, jobGroup, newCronExpression);
    }

    /**
     * 删除任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名称
     * @param jobGroup  任务组名称
     * @throws SchedulerException 调度异常
     */
    public static void deleteJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            log.info("任务删除成功: jobName={}, jobGroup={}", jobName, jobGroup);
        } else {
            log.warn("任务不存在: jobName={}, jobGroup={}", jobName, jobGroup);
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名称
     * @param jobGroup  任务组名称
     * @throws SchedulerException 调度异常
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            log.info("任务暂停成功: jobName={}, jobGroup={}", jobName, jobGroup);
        } else {
            log.warn("任务不存在: jobName={}, jobGroup={}", jobName, jobGroup);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名称
     * @param jobGroup  任务组名称
     * @throws SchedulerException 调度异常
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
            log.info("任务恢复成功: jobName={}, jobGroup={}", jobName, jobGroup);
        } else {
            log.warn("任务不存在: jobName={}, jobGroup={}", jobName, jobGroup);
        }
    }
}
