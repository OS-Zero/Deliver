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
import com.oszero.deliver.business.admin.model.dto.request.channelapp.*;
import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.channelapp.ChannelAppByChannelResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.channelapp.ChannelAppSearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.service.ChannelAppService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.common.enums.ChannelProviderTypeEnum;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.mapper.TemplateAppMapper;
import com.oszero.deliver.business.common.model.entity.ChannelApp;
import com.oszero.deliver.business.common.model.entity.TemplateApp;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ChannelAppServiceImpl extends ServiceImpl<ChannelAppMapper, ChannelApp>
        implements ChannelAppService {

    private final AppConfigUtils appConfigUtils;
    private final ChannelAppMapper channelAppMapper;
    private final TemplateAppMapper templateAppMapper;

    @Override
    public SearchResponseDto<ChannelAppSearchResponseDto> search(ChannelAppSearchRequestDto dto) {
        LambdaQueryWrapper<ChannelApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getAppName()), ChannelApp::getAppName, dto.getAppName())
                .eq(!Objects.isNull(dto.getChannelType()), ChannelApp::getChannelType, dto.getChannelType())
                .eq(!Objects.isNull(dto.getChannelProviderType()), ChannelApp::getChannelProviderType, dto.getChannelProviderType())
                .eq(!Objects.isNull(dto.getAppStatus()), ChannelApp::getAppStatus, dto.getAppStatus())
                .gt(!Objects.isNull(dto.getStartTime()), ChannelApp::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getStartTime()), ChannelApp::getCreateTime, dto.getEndTime())
                .eq(ChannelApp::getGroupId, GroupUtils.getGroupId())
                .orderByDesc(ChannelApp::getCreateTime);
        Page<ChannelApp> appPage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        channelAppMapper.selectPage(appPage, wrapper);
        List<ChannelAppSearchResponseDto> list = appPage.getRecords().stream()
                .map(app -> {
                    ChannelAppSearchResponseDto appSearchResponseDto = new ChannelAppSearchResponseDto();
                    BeanUtil.copyProperties(app, appSearchResponseDto);
                    appSearchResponseDto.setAppConfig(appConfigUtils.decryptAppConfig(app.getAppConfig()));
                    Integer channelType = app.getChannelType();
                    Integer channelProviderType = app.getChannelProviderType();
                    ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(channelType);
                    ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(channelProviderType);
                    if (Objects.isNull(channelTypeEnum) || Objects.isNull(channelProviderTypeEnum)) {
                        throw new BusinessException("参数异常");
                    }
                    appSearchResponseDto.setChannelTypeName(channelTypeEnum.getName());
                    appSearchResponseDto.setChannelProviderTypeName(channelProviderTypeEnum.getName());
                    return appSearchResponseDto;
                }).collect(Collectors.toList());
        return SearchResponseDto.<ChannelAppSearchResponseDto>builder()
                .records(list)
                .total(appPage.getTotal())
                .current(appPage.getCurrent())
                .size(appPage.getSize())
                .pages(appPage.getPages()).build();
    }

    @Override
    public void save(ChannelAppSaveRequestDto dto) {
        checkAppNameIsDuplicate(null, dto.getAppName());
        ChannelApp app = new ChannelApp();
        BeanUtil.copyProperties(dto, app);
        app.setAppConfig(appConfigUtils.encryptAppConfig(dto.getAppConfig()));
        app.setGroupId(GroupUtils.getGroupId());
        int insert = channelAppMapper.insert(app);
        if (DataBaseUtils.isSingleDataModifyFail(insert)) {
            throw new BusinessException("应用保存失败");
        }
    }

    @Override
    public void update(ChannelAppUpdateRequestDto dto) {
        checkAppNameIsDuplicate(dto.getAppId(), dto.getAppName());
        ChannelApp app = new ChannelApp();
        BeanUtil.copyProperties(dto, app);
        app.setAppConfig(appConfigUtils.encryptAppConfig(dto.getAppConfig()));
        int updateById = channelAppMapper.updateById(app);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("应用更新失败");
        }
    }

    @Override
    public void updateStatus(ChannelAppUpdateStatusRequestDto dto) {
        ChannelApp app = new ChannelApp();
        BeanUtil.copyProperties(dto, app);
        int updateById = channelAppMapper.updateById(app);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("应用状态更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteIdsRequestDto dto) {
        ArrayList<Long> errRes = new ArrayList<>();
        dto.getIds().forEach(appId -> {
            LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TemplateApp::getAppId, appId);
            long count = templateAppMapper.selectCount(wrapper);
            if (count > 0) {
                errRes.add(appId);
            }
        });
        if (!CollUtil.isEmpty(errRes)) {
            throw new BusinessException(String.format("以下应用%s已关联模板，请解除关联关系后再删除", errRes));
        }
        int deleted = channelAppMapper.deleteBatchIds(dto.getIds());
        if (DataBaseUtils.isBatchDataModifyFail(deleted, dto.getIds().size())) {
            throw new BusinessException("删除应用失败");
        }
    }

    @Override
    public List<ChannelAppByChannelResponseDto> getAppByChannel(ChannelAppByChannelRequestDto dto) {
        LambdaQueryWrapper<ChannelApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChannelApp::getChannelType, dto.getChannelType())
                .eq(ChannelApp::getChannelProviderType, dto.getChannelProviderType())
                .eq(ChannelApp::getGroupId, GroupUtils.getGroupId())
                .orderByDesc(ChannelApp::getCreateTime);
        List<ChannelApp> channelApps = channelAppMapper.selectList(wrapper);
        return channelApps.stream().map(app -> {
            ChannelAppByChannelResponseDto appByChannelResponseDto = new ChannelAppByChannelResponseDto();
            BeanUtil.copyProperties(app, appByChannelResponseDto);
            return appByChannelResponseDto;
        }).collect(Collectors.toList());
    }

    private void checkAppNameIsDuplicate(Long appId, String appName) {
        LambdaQueryWrapper<ChannelApp> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(appId)) {
            wrapper.eq(ChannelApp::getAppName, appName);
            long count = channelAppMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(String.format("应用名：'%s'已存在，请更换一个", appName));
            }
        } else {
            wrapper.eq(ChannelApp::getAppName, appName).or().eq(ChannelApp::getAppId, appId);
            long count = channelAppMapper.selectCount(wrapper);
            if (count > 1) {
                throw new BusinessException(String.format("应用名：'%s'已存在，请更换一个", appName));
            }
        }
    }
}




