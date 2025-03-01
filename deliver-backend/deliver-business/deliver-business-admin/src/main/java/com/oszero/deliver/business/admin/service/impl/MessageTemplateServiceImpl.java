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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.cache.AdminCacheManager;
import com.oszero.deliver.business.admin.constant.MessageParamConstant;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.messagetemplate.*;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.messagetemplate.MessageTemplateSearchByNameResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.messagetemplate.MessageTemplateSearchResponseDto;
import com.oszero.deliver.business.admin.service.MessageTemplateService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.admin.util.SendMessageUtils;
import com.oszero.deliver.business.common.enums.ChannelProviderTypeEnum;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.UsersTypeEnum;
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.mapper.MessageTemplateMapper;
import com.oszero.deliver.business.common.mapper.TemplateAppMapper;
import com.oszero.deliver.business.common.model.entity.database.ChannelApp;
import com.oszero.deliver.business.common.model.entity.database.MessageTemplate;
import com.oszero.deliver.business.common.model.entity.database.TemplateApp;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate>
        implements MessageTemplateService {

    private final MessageTemplateMapper messageTemplateMapper;
    private final ChannelAppMapper channelAppMapper;
    private final TemplateAppMapper templateAppMapper;
    private final SendMessageUtils sendMessageUtils;
    private final AdminCacheManager adminCacheManager;

    @Override
    public SearchResponseDto<MessageTemplateSearchResponseDto> search(MessageTemplateSearchRequestDto dto) {
        Page<MessageTemplate> templatePage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        LambdaQueryWrapper<MessageTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getTemplateName()), MessageTemplate::getTemplateName, dto.getTemplateName())
                .eq(!Objects.isNull(dto.getUsersType()), MessageTemplate::getUsersType, dto.getUsersType())
                .eq(!Objects.isNull(dto.getChannelType()), MessageTemplate::getChannelType, dto.getChannelType())
                .eq(!Objects.isNull(dto.getChannelProviderType()), MessageTemplate::getChannelProviderType, dto.getChannelProviderType())
                .eq(!Objects.isNull(dto.getMessageType()), MessageTemplate::getMessageType, dto.getMessageType())
                .eq(!Objects.isNull(dto.getTemplateStatus()), MessageTemplate::getTemplateStatus, dto.getTemplateStatus())
                .gt(!Objects.isNull(dto.getStartTime()), MessageTemplate::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getEndTime()), MessageTemplate::getCreateTime, dto.getEndTime())
                .eq(MessageTemplate::getGroupId, GroupUtils.getGroupId())
                .orderByDesc(MessageTemplate::getCreateTime);
        messageTemplateMapper.selectPage(templatePage, wrapper);
        List<MessageTemplateSearchResponseDto> messageTemplateSearchResponseDtoList = templatePage.getRecords()
                .stream()
                .map(template -> {
                    LambdaQueryWrapper<TemplateApp> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(TemplateApp::getTemplateId, template.getTemplateId());
                    TemplateApp templateApp = templateAppMapper.selectOne(queryWrapper);
                    ChannelApp app = channelAppMapper.selectById(templateApp.getAppId());
                    MessageTemplateSearchResponseDto templateSearchResponseDto = new MessageTemplateSearchResponseDto();
                    BeanUtil.copyProperties(template, templateSearchResponseDto);
                    UsersTypeEnum usersTypeEnum = UsersTypeEnum.getInstanceByCode(template.getUsersType());
                    ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(template.getChannelType());
                    ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(template.getChannelProviderType());
                    MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(template.getMessageType());
                    if (Objects.isNull(usersTypeEnum) || Objects.isNull(channelTypeEnum)
                        || Objects.isNull(channelProviderTypeEnum) || Objects.isNull(messageTypeEnum)) {
                        throw new BusinessException("消息模板信息异常");
                    }
                    templateSearchResponseDto.setUsersTypeName(usersTypeEnum.getName());
                    templateSearchResponseDto.setChannelTypeName(channelTypeEnum.getName());
                    templateSearchResponseDto.setChannelProviderTypeName(channelProviderTypeEnum.getName());
                    templateSearchResponseDto.setMessageTypeName(messageTypeEnum.getName());
                    templateSearchResponseDto.setAppId(app.getAppId());
                    templateSearchResponseDto.setAppName(app.getAppName());
                    return templateSearchResponseDto;
                }).toList();
        return SearchResponseDto.<MessageTemplateSearchResponseDto>builder()
                .records(messageTemplateSearchResponseDtoList)
                .total(templatePage.getTotal())
                .current(templatePage.getCurrent())
                .size(templatePage.getSize())
                .pages(templatePage.getPages()).build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(DeleteIdsRequestDto dto) {
        int deleteBatchIds = messageTemplateMapper.deleteBatchIds(dto.getIds());
        if (DataBaseUtils.isBatchDataModifyFail(deleteBatchIds, dto.getIds().size())) {
            throw new BusinessException("批量删除模板失败");
        }
        dto.getIds().forEach(templateId -> {
            LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TemplateApp::getTemplateId, templateId);
            int delete = templateAppMapper.delete(wrapper);
            if (DataBaseUtils.isSingleDataModifyFail(delete)) {
                throw new BusinessException("批量删除模板失败");
            }
        });
        adminCacheManager.evictBatchMessageTemplateCache(dto.getIds());
        adminCacheManager.evictBatchTemplateAppCache(dto.getIds());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(MessageTemplateUpdateRequestDto dto) {
        checkTemplateNameIsDuplicate(dto.getTemplateId(), dto.getTemplateName());
        MessageTemplate template = new MessageTemplate();
        BeanUtil.copyProperties(dto, template);
        int updateById = messageTemplateMapper.updateById(template);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("更新模板失败");
        }
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, dto.getTemplateId());
        TemplateApp templateApp = new TemplateApp();
        templateApp.setAppId(dto.getAppId());
        int update = templateAppMapper.update(templateApp, wrapper);
        if (DataBaseUtils.isSingleDataModifyFail(update)) {
            throw new BusinessException("更新模板失败");
        }
        adminCacheManager.evictMessageTemplateCache(dto.getTemplateId());
        adminCacheManager.evictTemplateAppCache(dto.getTemplateId());
    }

    @Override
    public void updateStatus(MessageTemplateUpdateStatusRequestDto dto) {
        MessageTemplate template = new MessageTemplate();
        BeanUtil.copyProperties(dto, template);
        int updateById = messageTemplateMapper.updateById(template);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("更新模板失败");
        }
        adminCacheManager.evictMessageTemplateCache(dto.getTemplateId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(MessageTemplateSaveRequestDto dto) {
        checkTemplateNameIsDuplicate(null, dto.getTemplateName());
        MessageTemplate template = new MessageTemplate();
        BeanUtil.copyProperties(dto, template);
        template.setGroupId(GroupUtils.getGroupId());
        int insert = messageTemplateMapper.insert(template);
        TemplateApp templateApp = new TemplateApp();
        templateApp.setTemplateId(template.getTemplateId());
        templateApp.setAppId(dto.getAppId());
        int save = templateAppMapper.insert(templateApp);
        if (DataBaseUtils.isSingleDataModifyFail(insert) || DataBaseUtils.isSingleDataModifyFail(save)) {
            throw new BusinessException("保存模板失败");
        }
    }

    @Override
    public void testSendMessage(SendMessageRequestDto sendMessageRequestDto) {
        sendMessageUtils.sendMessage(sendMessageRequestDto);
    }

    @Override
    public String getMessageParam(GetMessageParamRequestDto dto) {
        Long templateId = dto.getTemplateId();
        LambdaQueryWrapper<MessageTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageTemplate::getTemplateId, templateId)
                .eq(MessageTemplate::getGroupId, GroupUtils.getGroupId());
        MessageTemplate messageTemplate = messageTemplateMapper.selectOne(wrapper);
        if (Objects.isNull(messageTemplate)) {
            throw new BusinessException("请输入正确的模板ID");
        }
        return MessageParamConstant.getMessageParamJsonConfig(messageTemplate.getMessageType());
    }

    @Override
    public List<MessageTemplateSearchByNameResponseDto> searchByName(MessageTemplateSearchByNameRequestDto dto) {
        LambdaQueryWrapper<MessageTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageTemplate::getGroupId, GroupUtils.getGroupId())
                .like(MessageTemplate::getTemplateName, dto.getTemplateName())
                .orderByDesc(MessageTemplate::getCreateTime);
        List<MessageTemplate> messageTemplates = messageTemplateMapper.selectList(wrapper);
        if (!CollUtil.isEmpty(messageTemplates)) {
            return messageTemplates.stream().map(messageTemplate -> MessageTemplateSearchByNameResponseDto.builder()
                    .templateId(messageTemplate.getTemplateId())
                    .templateName(messageTemplate.getTemplateName()).build()).toList();
        }
        return List.of();
    }

    private void checkTemplateNameIsDuplicate(Long templateId, String templateName) {
        LambdaQueryWrapper<MessageTemplate> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(templateId)) {
            wrapper.eq(MessageTemplate::getTemplateName, templateName);
            Long count = messageTemplateMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(String.format("模板名：'%s'已存在，请更换一个", templateName));
            }
        } else {
            wrapper.eq(MessageTemplate::getTemplateName, templateName).or().eq(MessageTemplate::getTemplateId, templateId);
            Long count = messageTemplateMapper.selectCount(wrapper);
            if (count > 1) {
                throw new BusinessException(String.format("模板名：'%s'已存在，请更换一个", templateName));
            }
        }
    }
}




