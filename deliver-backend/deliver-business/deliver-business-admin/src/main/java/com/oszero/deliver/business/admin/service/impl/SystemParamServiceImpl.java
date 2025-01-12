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

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.admin.constant.AppConfigConstant;
import com.oszero.deliver.business.admin.constant.MessageParamConstant;
import com.oszero.deliver.business.admin.model.dto.request.systemparam.GetParamRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.*;
import com.oszero.deliver.business.admin.service.SystemParamService;
import com.oszero.deliver.business.common.enums.*;
import com.oszero.deliver.business.admin.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
public class SystemParamServiceImpl implements SystemParamService {
    @Override
    public List<GetChannelTypeResponseDto> getChannelType(GetParamRequestDto dto) {
        Integer usersType = dto.getUsersType();
        if (usersType == -1) { // 代表平台文件里获取渠道类型
            return Arrays.asList(
                    GetChannelTypeResponseDto.builder()
                            .channelType(Integer.valueOf(ChannelTypeEnum.DING.getCode()))
                            .channelTypeName(ChannelTypeEnum.DING.getName())
                            .build(),
                    GetChannelTypeResponseDto.builder()
                            .channelType(Integer.valueOf(ChannelTypeEnum.WECHAT.getCode()))
                            .channelTypeName(ChannelTypeEnum.WECHAT.getName())
                            .build(),
                    GetChannelTypeResponseDto.builder()
                            .channelType(Integer.valueOf(ChannelTypeEnum.FEI_SHU.getCode()))
                            .channelTypeName(ChannelTypeEnum.FEI_SHU.getName())
                            .build()
            );
        }
        UsersTypeEnum usersTypeEnum = UsersTypeEnum.getInstanceByCode(usersType);
        if (Objects.isNull(usersTypeEnum)) {
            throw new BusinessException("用户类型参数异常");
        }
        switch (usersTypeEnum) {
            case PHONE -> {
                return Arrays.asList(
                        GetChannelTypeResponseDto.builder()
                                .channelType(Integer.valueOf(ChannelTypeEnum.CALL.getCode()))
                                .channelTypeName(ChannelTypeEnum.CALL.getName())
                                .build(),
                        GetChannelTypeResponseDto.builder()
                                .channelType(Integer.valueOf(ChannelTypeEnum.SMS.getCode()))
                                .channelTypeName(ChannelTypeEnum.SMS.getName())
                                .build(),
                        GetChannelTypeResponseDto.builder()
                                .channelType(Integer.valueOf(ChannelTypeEnum.DING.getCode()))
                                .channelTypeName(ChannelTypeEnum.DING.getName())
                                .build(),
                        GetChannelTypeResponseDto.builder()
                                .channelType(Integer.valueOf(ChannelTypeEnum.WECHAT.getCode()))
                                .channelTypeName(ChannelTypeEnum.WECHAT.getName())
                                .build(),
                        GetChannelTypeResponseDto.builder()
                                .channelType(Integer.valueOf(ChannelTypeEnum.FEI_SHU.getCode()))
                                .channelTypeName(ChannelTypeEnum.FEI_SHU.getName())
                                .build()
                );
            }
            case EMAIL -> {
                return Collections.singletonList(GetChannelTypeResponseDto.builder()
                        .channelType(Integer.valueOf(ChannelTypeEnum.MAIL.getCode()))
                        .channelTypeName(ChannelTypeEnum.MAIL.getName())
                        .build());
            }
            case DING_USER_ID -> {
                return Collections.singletonList(GetChannelTypeResponseDto.builder()
                        .channelType(Integer.valueOf(ChannelTypeEnum.DING.getCode()))
                        .channelTypeName(ChannelTypeEnum.DING.getName())
                        .build());
            }
            case WECHAT_USER_ID -> {
                return Collections.singletonList(GetChannelTypeResponseDto.builder()
                        .channelType(Integer.valueOf(ChannelTypeEnum.WECHAT.getCode()))
                        .channelTypeName(ChannelTypeEnum.WECHAT.getName())
                        .build());
            }
            case FEI_SHU_USER_ID -> {
                return Collections.singletonList(GetChannelTypeResponseDto.builder()
                        .channelType(Integer.valueOf(ChannelTypeEnum.FEI_SHU.getCode()))
                        .channelTypeName(ChannelTypeEnum.FEI_SHU.getName())
                        .build());
            }
        }
        return List.of();
    }

    @Override
    public List<GetChannelProviderTypeResponseDto> getChannelProviderType(GetParamRequestDto dto) {
        Integer channelType = dto.getChannelType();
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(channelType);
        if (Objects.isNull(channelTypeEnum)) {
            throw new BusinessException("渠道类型参数异常");
        }
        switch (channelTypeEnum) {
            case CALL, SMS -> {
                return Arrays.asList(
                        GetChannelProviderTypeResponseDto.builder()
                                .channelProviderType(Integer.valueOf(ChannelProviderTypeEnum.ALI.getCode()))
                                .channelProviderTypeName(ChannelProviderTypeEnum.ALI.getName())
                                .build(),
                        GetChannelProviderTypeResponseDto.builder()
                                .channelProviderType(Integer.valueOf(ChannelProviderTypeEnum.TENCENT.getCode()))
                                .channelProviderTypeName(ChannelProviderTypeEnum.TENCENT.getName())
                                .build()
                );
            }
            default -> {
                return Collections.singletonList(
                        GetChannelProviderTypeResponseDto.builder()
                                .channelProviderType(Integer.valueOf(ChannelProviderTypeEnum.DEFAULT.getCode()))
                                .channelProviderTypeName(ChannelProviderTypeEnum.DEFAULT.getName())
                                .build()
                );
            }
        }
    }

    @Override
    public List<GetMessageTypeResponseDto> getMessageType(GetParamRequestDto dto) {
        Integer channelType = dto.getChannelType();
        Integer channelProviderType = dto.getChannelProviderType();
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(channelType);
        ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(channelProviderType);
        if (Objects.isNull(channelTypeEnum) || Objects.isNull(channelProviderTypeEnum)) {
            throw new BusinessException("渠道参数异常");
        }
        return Arrays.stream(MessageTypeEnum.values())
                .filter(messageTypeEnum ->
                        StrUtil.equals(String.valueOf(channelType), messageTypeEnum.getChannelType())
                                && StrUtil.equals(String.valueOf(channelProviderType), messageTypeEnum.getChannelProviderType()))
                .map(messageTypeEnum -> GetMessageTypeResponseDto.builder()
                        .messageType(messageTypeEnum.getCode())
                        .messageTypeName(messageTypeEnum.getName()).build())
                .toList();
    }

    @Override
    public String getAppConfig(GetParamRequestDto dto) {
        Integer channelType = dto.getChannelType();
        Integer channelProviderType = dto.getChannelProviderType();
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(channelType);
        ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(channelProviderType);
        if (Objects.isNull(channelTypeEnum) || Objects.isNull(channelProviderTypeEnum)) {
            throw new BusinessException("参数异常");
        }
        switch (channelTypeEnum) {
            case CALL -> {
                if (channelProviderTypeEnum == ChannelProviderTypeEnum.ALI) {
                    return AppConfigConstant.ALI_YUN_CALL_CONFIG;
                } else if (channelProviderTypeEnum == ChannelProviderTypeEnum.TENCENT) {
                    return AppConfigConstant.TENCENT_CALL_CONFIG;
                }
            }
            case SMS -> {
                if (channelProviderTypeEnum == ChannelProviderTypeEnum.ALI) {
                    return AppConfigConstant.ALI_YUN_SMS_CONFIG;
                } else if (channelProviderTypeEnum == ChannelProviderTypeEnum.TENCENT) {
                    return AppConfigConstant.TENCENT_SMS_CONFIG;
                }
            }
            case MAIL -> {
                return AppConfigConstant.MAIL_CONFIG;
            }
            case DING -> {
                return AppConfigConstant.DING_CONFIG;
            }
            case WECHAT -> {
                return AppConfigConstant.WECHAT_CONFIG;
            }
            case FEI_SHU -> {
                return AppConfigConstant.FEI_SHU_CONFIG;
            }
        }
        return AppConfigConstant.NO_CONFIG;
    }

    @Override
    public List<GetPlatformFileTypeResponseDto> getPlatformFileType(GetParamRequestDto dto) {
        if (dto.getChannelType() == null) {
            throw new BusinessException("参数异常");
        }
        return Arrays.stream(PlatformFileTypeEnum.values())
                .filter(item -> StrUtil.equals(item.getChannelType(), String.valueOf(dto.getChannelType())))
                .map(item -> GetPlatformFileTypeResponseDto.builder()
                        .platformFileType(item.getCode())
                        .platformFileTypeName(item.getName())
                        .build())
                .toList();
    }

    @Override
    public String getMessageParam(GetParamRequestDto dto) {
        if (Objects.isNull(dto.getMessageType())) {
            throw new BusinessException("参数异常");
        }
        return MessageParamConstant.getMessageParamJsonConfig(dto.getMessageType());
    }
}
