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

package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.AppByChannelResponseDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;

import java.util.List;

/**
 * app service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface AppService extends IService<App> {

    /**
     * 分页查询 APP
     *
     * @param appSearchRequestDto 参数
     * @return Page
     */
    Page<AppSearchResponseDto> getAppPagesByCondition(AppSearchRequestDto appSearchRequestDto);

    /**
     * 批量删除
     *
     * @param dto ID list
     */
    void deleteByIds(DeleteIdsRequestDto dto);

    /**
     * 更新通过 ID
     *
     * @param dto 参数
     */
    void updateById(AppSaveAndUpdateRequestDto dto);

    /**
     * 保存
     *
     * @param dto 参数
     */
    void save(AppSaveAndUpdateRequestDto dto);

    /**
     * 获取 APP 通过渠道类型
     *
     * @param dto 参数
     * @return APP
     */
    List<AppByChannelResponseDto> getAppByChannelType(TemplateAddGetByChannelRequestDto dto);

    /**
     * 更新 APP 状态通过 ID
     *
     * @param dto 参数
     */
    void updateStatusById(AppUpdateStatusRequestDto dto);

    /**
     * 获取 APP 配置通过渠道
     *
     * @param dto 渠道 dto
     * @return APP 配置
     */
    String getAppConfigByChannelType(AppConfigByChannelRequestDto dto);
}
