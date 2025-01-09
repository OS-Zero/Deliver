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

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.admin.constant.JobConstant;
import com.oszero.deliver.business.admin.model.entity.database.SendTask;
import com.oszero.deliver.business.admin.model.json.SendTaskParam;
import com.oszero.deliver.business.admin.service.impl.SendTaskServiceImpl;
import com.oszero.deliver.business.common.constant.CommonConstant;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

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

    public boolean isCronJob(SendTask sendTask) {
        return sendTask.getTaskType() == JobConstant.CRON_TIME;
    }

    public void addJob(SendTask sendTask) throws SchedulerException {
        String taskParam = sendTask.getTaskParam();
        SendTaskParam sendTaskParam = JSONUtil.toBean(taskParam, SendTaskParam.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstant.TASK_ID, sendTask.getTaskId());
        QuartzJobUtils.addJobWithParams(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME,
                sendTaskParam.getCronExpression(), jobDataMap, SendTaskServiceImpl.class);
    }

    public void deleteJob(SendTask sendTask) throws SchedulerException {
        QuartzJobUtils.deleteJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
    }

    public void updateJobStatus(SendTask sendTask) throws SchedulerException {
        if (sendTask.getTaskStatus() == CommonConstant.ENABLE_STATUS) {
            QuartzJobUtils.resumeJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
        } else if (sendTask.getTaskStatus() == CommonConstant.DISABLE_STATUS){
            QuartzJobUtils.pauseJob(scheduler, sendTask.getTaskName(), JobConstant.JOB_GROUP_NAME);
        }
    }
}
