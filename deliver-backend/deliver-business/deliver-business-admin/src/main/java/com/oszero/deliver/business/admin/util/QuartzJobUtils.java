/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.admin.util;

import cn.hutool.core.util.StrUtil;
import org.quartz.*;

import java.util.Date;

/**
 * @author oszero
 * @version 1.0.0
 */
public class QuartzJobUtils {

    private static final String TRIGGER_PRE_NAME = "trigger";

    private QuartzJobUtils() {}

    public static void addJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression, Class<? extends Job> jobClass) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(jobName, jobGroup)
            .build();
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(TRIGGER_PRE_NAME + jobName, jobGroup)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void addJobWithParams(Scheduler scheduler, String jobName, String jobGroup, String cronExpression,
                                        JobDataMap jobDataMap, Class<? extends Job> jobClass) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(jobName, jobGroup)
            .setJobData(jobDataMap)
            .build();
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(TRIGGER_PRE_NAME + jobName, jobGroup)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void addSingleExecutionJob(Scheduler scheduler, String jobName, String jobGroup, Date executionTime,
                                             JobDataMap jobDataMap, Class<? extends Job> jobClass) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .setJobData(jobDataMap)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_PRE_NAME + jobName, jobGroup)
                .startAt(executionTime)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void updateJobCron(Scheduler scheduler, String jobName, String jobGroup, String newCronExpression) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(TRIGGER_PRE_NAME + jobName, jobGroup);
        if (!scheduler.checkExists(triggerKey)) {
            return;
        }
        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (StrUtil.equals(oldTrigger.getCronExpression(), newCronExpression)) {
            return;
        }
        Trigger newTrigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(newCronExpression))
            .build();
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    public static void deleteJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
        }
    }

    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
        }
    }
}
