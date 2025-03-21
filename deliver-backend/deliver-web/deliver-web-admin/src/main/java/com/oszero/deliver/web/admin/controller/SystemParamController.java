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

package com.oszero.deliver.web.admin.controller;

import com.oszero.deliver.business.admin.model.dto.request.systemparam.GetParamRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetChannelProviderTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetChannelTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetMessageTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetPlatformFileTypeResponseDto;
import com.oszero.deliver.business.admin.service.SystemParamService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class SystemParamController {

    private final SystemParamService systemParamService;

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_CHANNEL_TYPE)
    public CommonResult<List<GetChannelTypeResponseDto>> getChannelType(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getChannelType(dto));
    }

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_CHANNEL_PROVIDER_TYPE)
    public CommonResult<List<GetChannelProviderTypeResponseDto>> getChannelProviderType(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getChannelProviderType(dto));
    }

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_MESSAGE_TYPE)
    public CommonResult<List<GetMessageTypeResponseDto>> getMessageType(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getMessageType(dto));
    }

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_APP_CONFIG)
    public CommonResult<String> getAppConfig(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getAppConfig(dto));
    }

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_PLATFORM_FILE_TYPE)
    public CommonResult<List<GetPlatformFileTypeResponseDto>> getPlatformFileType(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getPlatformFileType(dto));
    }

    @PostMapping(AdminPathConstant.SYSTEM_PARAM_GET_MESSAGE_PARAM)
    public CommonResult<String> getMessageParam(@RequestBody GetParamRequestDto dto) {
        return CommonResult.success(systemParamService.getMessageParam(dto));
    }
}
