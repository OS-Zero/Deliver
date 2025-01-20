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

package com.oszero.deliver.business.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.mapper.MessageTemplateMapper;
import com.oszero.deliver.business.common.mapper.TemplateAppMapper;
import com.oszero.deliver.business.common.model.entity.ChannelApp;
import com.oszero.deliver.business.common.model.entity.MessageTemplate;
import com.oszero.deliver.business.common.model.entity.TemplateApp;
import com.oszero.deliver.business.common.util.DoubleStatusUtils;
import com.oszero.deliver.business.common.util.IpUtils;
import com.oszero.deliver.business.common.util.TraceIdUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.LinkHandler;
import com.oszero.deliver.business.server.service.SendService;
import com.oszero.deliver.business.server.util.SendTaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final MessageTemplateMapper messageTemplateMapper;
    private final ChannelAppMapper channelAppMapper;
    private final TemplateAppMapper templateAppMapper;
    private final LinkHandler linkHandler;

    @Override
    public String send(SendRequestDto sendRequestDto) {
        Long templateId = sendRequestDto.getTemplateId();
        List<String> users = sendRequestDto.getUsers();
        Map<String, Object> messageParam = sendRequestDto.getMessageParam();
        Integer retryCount = sendRequestDto.getRetryCount();

        SendTaskDto sendTaskDto = new SendTaskDto();
        SendTaskUtils.setSendTaskDto(sendTaskDto);
        sendTaskDto.setTemplateId(templateId);
        sendTaskDto.setMessageParam(messageParam);
        sendTaskDto.setTraceId(TraceIdUtils.getTraceId());
        sendTaskDto.setClientIp(IpUtils.getClientIp());
        sendTaskDto.setUsers(users);
        sendTaskDto.setRetried(0);
        sendTaskDto.setRetryCount(retryCount);

        MessageTemplate template = messageTemplateMapper.selectById(templateId);
        if (Objects.isNull(template)) {
            throw new MessageException("传入的模板ID非法，请输入正确的templateId");
        }
        Integer channelType = template.getChannelType();
        Integer channelProviderType = template.getChannelProviderType();
        String messageType = template.getMessageType();
        Integer usersType = template.getUsersType();
        sendTaskDto.setUsersType(usersType);
        sendTaskDto.setChannelType(channelType);
        sendTaskDto.setChannelProviderType(channelProviderType);
        sendTaskDto.setMessageType(messageType);

        if (DoubleStatusUtils.disable(template.getTemplateStatus())) {
            throw new MessageException("此模板已禁用，再次使用请启用此模板");
        }

        LambdaQueryWrapper<TemplateApp> templateAppLambdaQueryWrapper = new LambdaQueryWrapper<>();
        templateAppLambdaQueryWrapper.eq(TemplateApp::getTemplateId, templateId);
        TemplateApp templateApp = templateAppMapper.selectOne(templateAppLambdaQueryWrapper);
        if (Objects.isNull(templateApp)) {
            throw new MessageException("未获取到模板所关联的应用，请检查关联的应用是否存在");
        }
        Long appId = templateApp.getAppId();
        sendTaskDto.setAppId(appId);

        ChannelApp channelApp = channelAppMapper.selectById(appId);
        if (Objects.isNull(channelApp)) {
            throw new MessageException("未获取到模板所关联的应用，请检查关联的应用是否存在");
        }

        if (DoubleStatusUtils.disable(channelApp.getAppStatus())) {
            throw new MessageException("模板关联的应用为禁用状态，再次使用请启用");
        }
        sendTaskDto.setAppConfig(channelApp.getAppConfig());

        LinkContext<SendTaskDto> context = LinkContext.<SendTaskDto>builder()
                .processModel(sendTaskDto)
                .code(usersType + "-" + channelType).build();
        linkHandler.process(context);

        return sendTaskDto.getTraceId();
    }
}
