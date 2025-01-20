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
        if (UserUtils.isAdminUser()) {
            List<StartupResponseDto.CurrentLoginUserMenu> currentLoginUserMenuList = Stream.of("分组管理", "系统管理")
                    .map(menuName -> StartupResponseDto.CurrentLoginUserMenu.builder().menuName(menuName).build()).toList();
            startupResponseDto.setCurrentLoginUserMenuList(currentLoginUserMenuList);
        } else {
            List<StartupResponseDto.CurrentLoginUserMenu> currentLoginUserMenuList = Stream.of("分组管理", "系统管理")
                    .map(menuName -> StartupResponseDto.CurrentLoginUserMenu.builder().menuName(menuName).build()).toList();
            startupResponseDto.setCurrentLoginUserMenuList(currentLoginUserMenuList);
        }
        startupResponseDto.setUsersTypeParamList(Arrays.stream(UsersTypeEnum.values()).map(usersTypeEnum -> StartupResponseDto.UsersTypeParam.builder()
                .usersType(Integer.valueOf(usersTypeEnum.getCode()))
                .usersTypeName(usersTypeEnum.getName())
                .build()).toList());
        return startupResponseDto;
    }
}
