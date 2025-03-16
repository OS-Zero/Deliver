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

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.admin.constant.UserMenuConstant;
import com.oszero.deliver.business.admin.model.dto.response.startup.StartupResponseDto;
import com.oszero.deliver.business.admin.service.StartupService;
import com.oszero.deliver.business.admin.util.UserUtils;
import com.oszero.deliver.business.common.enums.UsersTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StartupServiceImpl implements StartupService {

    @Override
    public StartupResponseDto startup() {
        StartupResponseDto startupResponseDto = new StartupResponseDto();
        String currentLoginUserMenu = "{}";
        if (UserUtils.isAdminUser()) {
            currentLoginUserMenu = UserMenuConstant.USER_MENU_ADMIN;
        } else {
            currentLoginUserMenu = UserMenuConstant.USER_MENU_ORDINARY;
        }
        startupResponseDto.setCurrentLoginUserMenu(JSONUtil.toBean(currentLoginUserMenu, JSONObject.class));
        startupResponseDto.setUsersTypeParamList(Arrays.stream(UsersTypeEnum.values()).map(usersTypeEnum -> StartupResponseDto.UsersTypeParam.builder()
                .usersType(Integer.valueOf(usersTypeEnum.getCode()))
                .usersTypeName(usersTypeEnum.getName())
                .build()).toList());
        return startupResponseDto;
    }
}
