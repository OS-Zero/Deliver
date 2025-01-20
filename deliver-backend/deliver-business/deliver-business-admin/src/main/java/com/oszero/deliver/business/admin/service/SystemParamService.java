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

package com.oszero.deliver.business.admin.service;

import com.oszero.deliver.business.admin.model.dto.request.systemparam.GetParamRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetChannelProviderTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetChannelTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetMessageTypeResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.systemparam.GetPlatformFileTypeResponseDto;

import java.util.List;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface SystemParamService {
    List<GetChannelTypeResponseDto> getChannelType(GetParamRequestDto dto);
    List<GetChannelProviderTypeResponseDto> getChannelProviderType(GetParamRequestDto dto);
    List<GetMessageTypeResponseDto> getMessageType(GetParamRequestDto dto);
    String getAppConfig(GetParamRequestDto dto);
    List<GetPlatformFileTypeResponseDto> getPlatformFileType(GetParamRequestDto dto);
    String getMessageParam(GetParamRequestDto dto);
}
