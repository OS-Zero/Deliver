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

import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.DashboardHeadDataResponseDto;
import com.oszero.deliver.admin.model.dto.response.DashboardInfoResponseDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseReactDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseVueDto;

import java.util.List;

/**
 * 仪表盘 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface DashboardService {
    /**
     * 获取仪表盘头部数据
     *
     * @return 头部数据
     */
    DashboardHeadDataResponseDto getDashboardHeadData();

    /**
     * 获取消息详情 vue 版本
     *
     * @param dto 日期选择
     * @return 消息详情
     */
    MessageInfoResponseVueDto getMessageInfoVue(DashboardDateSelectRequestDto dto);

    /**
     * 获取消息详情 React 版本
     *
     * @param dto 日期选择
     * @return 消息详情
     */
    List<MessageInfoResponseReactDto> getMessageInfoReact(DashboardDateSelectRequestDto dto);

    /**
     * 获取模版详情
     *
     * @param dto 日期选择
     * @return 模版详情
     */
    DashboardInfoResponseDto getTemplateInfo(DashboardDateSelectRequestDto dto);

    /**
     * 获取 APP 详情
     *
     * @param dto 日期选择
     * @return APP 详情
     */
    DashboardInfoResponseDto getAppInfo(DashboardDateSelectRequestDto dto);

    /**
     * 获取推送用户详情
     *
     * @param dto 日期选择
     * @return 推送用户详情
     */
    DashboardInfoResponseDto getPushUserInfo(DashboardDateSelectRequestDto dto);
}
