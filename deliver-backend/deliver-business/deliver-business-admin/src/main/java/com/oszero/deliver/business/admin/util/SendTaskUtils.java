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

import com.oszero.deliver.business.admin.constant.AdminConstant;
import com.oszero.deliver.business.admin.constant.JobConstant;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.model.entity.database.SendTask;
import com.oszero.deliver.business.admin.service.impl.SendTaskServiceImpl;
import com.oszero.deliver.business.common.constant.CommonConstant;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SendTaskUtils {

    private final SchedulerFactoryBean schedulerFactoryBean;
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        scheduler = schedulerFactoryBean.getScheduler();
    }

    public boolean isTimeJob(SendTask sendTask) {
        Integer taskType = sendTask.getTaskType();
        return taskType == JobConstant.CRON_TIME || taskType == JobConstant.SINGLE_TIME;
    }

    private boolean isCronJob(SendTask sendTask) {
        Integer taskType = sendTask.getTaskType();
        return taskType == JobConstant.CRON_TIME;
    }

    private boolean isSingleJob(SendTask sendTask) {
        Integer taskType = sendTask.getTaskType();
        return taskType == JobConstant.SINGLE_TIME;
    }

    public static boolean isValidCron(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    public void addJob(SendTask sendTask) throws SchedulerException, ParseException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstant.TASK_ID, sendTask.getTaskId());
        String taskTimeExpression = sendTask.getTaskTimeExpression();
        if (isCronJob(sendTask)) {
            if (!isValidCron(taskTimeExpression)) {
                throw new BusinessException("cron表达式不合法");
            }
            QuartzJobUtils.addJobWithParams(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME,
                    taskTimeExpression, jobDataMap, SendTaskServiceImpl.class);
        } else if (isSingleJob(sendTask)) {
            SimpleDateFormat sdf = new SimpleDateFormat(AdminConstant.DATE_FORMAT);
            Date date = sdf.parse(taskTimeExpression);
            if (date.before(new Date(System.currentTimeMillis()))) {
                throw new BusinessException("定时时间不能小于当前时间");
            }
            QuartzJobUtils.addSingleExecutionJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME,
                    date, jobDataMap, SendTaskServiceImpl.class);
        }
    }

    public void deleteJob(SendTask sendTask) throws SchedulerException {
        QuartzJobUtils.deleteJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
    }

    public void updateJobStatus(SendTask sendTask) throws SchedulerException {
        if (sendTask.getTaskStatus() == CommonConstant.ENABLE_STATUS) {
            QuartzJobUtils.resumeJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
        } else if (sendTask.getTaskStatus() == CommonConstant.DISABLE_STATUS) {
            QuartzJobUtils.pauseJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
        }
    }
}
