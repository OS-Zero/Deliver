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

package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.common.CommonResult;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.AppByChannelResponseDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.service.AppService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * APP 控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    /**
     * 分页搜索 APP
     *
     * @param appSearchRequestDto dto
     * @return Page对象
     */
    @PostMapping("/search")
    public CommonResult<Page<AppSearchResponseDto>> getAppPagesByCondition(@RequestBody AppSearchRequestDto appSearchRequestDto) {
        Page<AppSearchResponseDto> page = appService.getAppPagesByCondition(appSearchRequestDto);
        return CommonResult.success(page);
    }

    /**
     * 保存 APP
     *
     * @param dto dto
     * @return 成功
     */
    @PostMapping("/save")
    public CommonResult<?> save(@Valid @RequestBody AppSaveAndUpdateRequestDto dto) {
        appService.save(dto);
        return CommonResult.success();
    }

    /**
     * 更新通过 ID
     *
     * @param dto dto
     * @return 成功
     */
    @PostMapping("/updateById")
    public CommonResult<?> updateById(@Valid @RequestBody AppSaveAndUpdateRequestDto dto) {
        appService.updateById(dto);
        return CommonResult.success();
    }

    /**
     * 更新 APP 状态通过 ID
     *
     * @param dto dto
     * @return 成功
     */
    @PostMapping("/updateStatusById")
    public CommonResult<?> updateStatusById(@Valid @RequestBody AppUpdateStatusRequestDto dto) {
        appService.updateStatusById(dto);
        return CommonResult.success();
    }

    /**
     * 批量删除 APP
     *
     * @param dto dto
     * @return 成功
     */
    @PostMapping("/deleteByIds")
    public CommonResult<?> deleteByIds(@Valid @RequestBody DeleteIdsRequestDto dto) {
        appService.deleteByIds(dto);
        return CommonResult.success();
    }

    /**
     * 获取 APP 通过渠道类型
     *
     * @param dto dto
     * @return APP 列表
     */
    @PostMapping("/getAppByChannelType")
    public CommonResult<List<AppByChannelResponseDto>> getAppByChannelType(@Valid @RequestBody TemplateAddGetByChannelRequestDto dto) {
        List<AppByChannelResponseDto> appList = appService.getAppByChannelType(dto);
        return CommonResult.success(appList);
    }

    /**
     * 获取应用配置通过渠道类型
     *
     * @param dto AppConfigByChannelRequestDto
     * @return 应用配置
     */
    @PostMapping("/getAppConfigByChannelType")
    public CommonResult<String> getAppConfigByChannelType(@Valid @RequestBody AppConfigByChannelRequestDto dto) {
        return CommonResult.success(appService.getAppConfigByChannelType(dto));
    }
}
