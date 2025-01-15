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

package com.oszero.deliver.business.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.constant.JobConstant;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.mapper.PeopleGroupMapper;
import com.oszero.deliver.business.admin.mapper.SendTaskMapper;
import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.messagetemplate.SendMessageRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.sendtask.*;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.sendtask.SendTaskSearchResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.PeopleGroup;
import com.oszero.deliver.business.admin.model.entity.database.SendTask;
import com.oszero.deliver.business.admin.service.SendTaskService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.admin.util.SendMessageUtils;
import com.oszero.deliver.business.admin.util.SendTaskUtils;
import com.oszero.deliver.business.common.constant.CommonConstant;
import com.oszero.deliver.business.common.mapper.MessageTemplateMapper;
import com.oszero.deliver.business.common.model.entity.MessageTemplate;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SendTaskServiceImpl extends ServiceImpl<SendTaskMapper, SendTask>
        implements SendTaskService {

    private final SendTaskMapper sendTaskMapper;
    private final PeopleGroupMapper peopleGroupMapper;
    private final MessageTemplateMapper messageTemplateMapper;
    private final SendMessageUtils sendMessageUtils;
    private final SendTaskUtils sendTaskUtils;

    @Override
    public SearchResponseDto<SendTaskSearchResponseDto> search(SendTaskSearchRequestDto dto) {
        LambdaQueryWrapper<SendTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getTaskName()), SendTask::getTaskName, dto.getTaskName())
                .eq(!Objects.isNull(dto.getTaskType()), SendTask::getTaskType, dto.getTaskType())
                .eq(!Objects.isNull(dto.getTaskStatus()), SendTask::getTaskStatus, dto.getTaskStatus())
                .gt(!Objects.isNull(dto.getStartTime()), SendTask::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getEndTime()), SendTask::getCreateTime, dto.getEndTime())
                .eq(SendTask::getGroupId, GroupUtils.getGroupId())
                .orderByDesc(SendTask::getCreateTime);
        Page<SendTask> page = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        sendTaskMapper.selectPage(page, wrapper);
        List<SendTaskSearchResponseDto> list = page.getRecords().stream()
                .map(task -> {
                    SendTaskSearchResponseDto sendTaskSearchResponseDto = new SendTaskSearchResponseDto();
                    BeanUtil.copyProperties(task, sendTaskSearchResponseDto);
                    sendTaskSearchResponseDto.setTemplateName(messageTemplateMapper.selectById(task.getTemplateId()).getTemplateName());
                    sendTaskSearchResponseDto.setPeopleGroupName(peopleGroupMapper.selectById(task.getPeopleGroupId()).getPeopleGroupName());
                    return sendTaskSearchResponseDto;
                }).toList();
        return SearchResponseDto.<SendTaskSearchResponseDto>builder()
                .records(list)
                .total(page.getTotal())
                .current(page.getCurrent())
                .size(page.getSize())
                .pages(page.getPages()).build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(SendTaskSaveRequestDto dto) throws SchedulerException, ParseException {
        checkNameIsDuplicate(null, dto.getTaskName());
        checkUsersType(dto.getTemplateId(), dto.getPeopleGroupId());
        SendTask sendTask = new SendTask();
        // 默认启用
        sendTask.setTaskStatus(CommonConstant.ENABLE_STATUS);
        BeanUtil.copyProperties(dto, sendTask);
        sendTask.setGroupId(GroupUtils.getGroupId());
        int insert = sendTaskMapper.insert(sendTask);
        if (DataBaseUtils.isSingleDataModifyFail(insert)) {
            throw new BusinessException("保存任务失败");
        }
        if (sendTaskUtils.isTimeJob(sendTask)) {
            sendTaskUtils.addJob(sendTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(SendTaskUpdateRequestDto dto) throws SchedulerException, ParseException {
        checkNameIsDuplicate(dto.getTaskId(), dto.getTaskName());
        checkUsersType(dto.getTemplateId(), dto.getPeopleGroupId());
        SendTask sendTask = new SendTask();
        BeanUtil.copyProperties(dto, sendTask);
        int updateById = sendTaskMapper.updateById(sendTask);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("更新任务失败");
        }
        if (sendTaskUtils.isTimeJob(sendTask)) {
            sendTaskUtils.deleteJob(sendTask);
            sendTaskUtils.addJob(sendTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateStatus(SendTaskUpdateStatusRequestDto dto) throws SchedulerException {
        SendTask sendTask = new SendTask();
        BeanUtil.copyProperties(dto, sendTask);
        int updateById = sendTaskMapper.updateById(sendTask);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("更新任务失败");
        }
        sendTask = sendTaskMapper.selectById(dto.getTaskId());
        if (sendTaskUtils.isTimeJob(sendTask)) {
            sendTaskUtils.updateJobStatus(sendTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(DeleteIdsRequestDto dto) throws SchedulerException {
        for (int i = 0; i < dto.getIds().size(); i++) {
            SendTask sendTask = sendTaskMapper.selectById(dto.getIds().get(i));
            if (sendTaskUtils.isTimeJob(sendTask)) {
                sendTaskUtils.deleteJob(sendTask);
            }
        }
        int deleteBatchIds = sendTaskMapper.deleteBatchIds(dto.getIds());
        if (DataBaseUtils.isBatchDataModifyFail(deleteBatchIds, dto.getIds().size())) {
            throw new BusinessException("批量删除任务失败");
        }
    }

    @Override
    public void sendRealTimeMessage(SendTaskSendMessageRequestDto dto) {
        sendMessage(dto.getTaskId());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long taskId = jobDataMap.getLong(JobConstant.TASK_ID);
        sendMessage(taskId);
    }

    private void sendMessage(Long taskId) {
        SendTask sendTask = sendTaskMapper.selectById(taskId);
        Long templateId = sendTask.getTemplateId();
        Long peopleGroupId = sendTask.getPeopleGroupId();
        String taskMessageParam = sendTask.getTaskMessageParam();
        Map<String, Object> messageParam = JSONUtil.toBean(taskMessageParam, Map.class, true);
        PeopleGroup peopleGroup = peopleGroupMapper.selectById(peopleGroupId);
        List<String> users = Arrays.stream(peopleGroup.getPeopleGroupList().split(",")).toList();
        SendMessageRequestDto sendMessageRequestDto = new SendMessageRequestDto();
        sendMessageRequestDto.setTemplateId(templateId);
        sendMessageRequestDto.setMessageParam(messageParam);
        sendMessageRequestDto.setUsers(users);
        sendMessageUtils.sendMessage(sendMessageRequestDto);
    }

    private void checkNameIsDuplicate(Long taskId, String taskName) {
        LambdaQueryWrapper<SendTask> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(taskId)) {
            wrapper.eq(SendTask::getTaskName, taskName);
            Long count = sendTaskMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(String.format("任务名：'%s'已存在，请更换一个", taskName));
            }
        } else {
            wrapper.eq(SendTask::getTaskName, taskName).or().eq(SendTask::getTaskId, taskId);
            Long count = sendTaskMapper.selectCount(wrapper);
            if (count > 1) {
                throw new BusinessException(String.format("任务名：'%s'已存在，请更换一个", taskName));
            }
        }
    }

    private void checkUsersType(Long templateId, Long peopleGroupId) {
        LambdaQueryWrapper<MessageTemplate> messageTemplateWrapper = new LambdaQueryWrapper<>();
        messageTemplateWrapper.eq(MessageTemplate::getTemplateId, templateId)
                .eq(MessageTemplate::getGroupId, GroupUtils.getGroupId());
        MessageTemplate messageTemplate = messageTemplateMapper.selectOne(messageTemplateWrapper);
        if (Objects.isNull(messageTemplate)) {
            throw new BusinessException("请输入正确的模板ID");
        }
        LambdaQueryWrapper<PeopleGroup> peopleGroupWrapper = new LambdaQueryWrapper<>();
        peopleGroupWrapper.eq(PeopleGroup::getPeopleGroupId, peopleGroupId)
                .eq(PeopleGroup::getGroupId, GroupUtils.getGroupId());
        PeopleGroup peopleGroup = peopleGroupMapper.selectOne(peopleGroupWrapper);
        if (Objects.isNull(peopleGroup)) {
            throw new BusinessException("请输入正确的人群ID");
        }
        if (!NumberUtil.equals(messageTemplate.getUsersType(), peopleGroup.getUsersType())) {
            throw new BusinessException("关联的人群类型与消息模板中的人群类型不匹配");
        }
    }
}