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
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.constant.PlatformFileConstant;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.mapper.PlatformFileMapper;
import com.oszero.deliver.business.admin.model.dto.request.platformfile.PlatformFileSearchRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.platformfile.PlatformFileUploadRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.platformfile.PlatformFileSearchResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.PlatformFile;
import com.oszero.deliver.business.admin.service.PlatformFileService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.common.constant.CommonConstant;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.enums.PlatformFileTypeEnum;
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.model.entity.ChannelApp;
import com.oszero.deliver.business.common.util.AesUtils;
import com.oszero.deliver.platformclient.client.ding.DingClient;
import com.oszero.deliver.platformclient.client.feishu.FeiShuClient;
import com.oszero.deliver.platformclient.client.wechat.WeChatClient;
import com.oszero.deliver.platformclient.model.app.DingApp;
import com.oszero.deliver.platformclient.model.app.FeiShuApp;
import com.oszero.deliver.platformclient.model.app.WeChatApp;
import com.oszero.deliver.platformclient.model.dto.PlatformFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PlatformFileServiceImpl extends ServiceImpl<PlatformFileMapper, PlatformFile>
        implements PlatformFileService {

    private final PlatformFileMapper platformFileMapper;
    private final DingClient dingClient;
    private final FeiShuClient feiShuClient;
    private final WeChatClient weChatClient;
    private final ChannelAppMapper channelAppMapper;
    private final AesUtils aesUtils;

    @Override
    public SearchResponseDto<PlatformFileSearchResponseDto> search(PlatformFileSearchRequestDto dto) {
        LambdaQueryWrapper<PlatformFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getPlatformFileName()), PlatformFile::getPlatformFileName, dto.getPlatformFileName())
                .eq(!Objects.isNull(dto.getChannelType()), PlatformFile::getChannelType, dto.getChannelType())
                .eq(StrUtil.isNotBlank(dto.getPlatformFileType()), PlatformFile::getPlatformFileType, dto.getPlatformFileType())
                .eq(!Objects.isNull(dto.getAppId()), PlatformFile::getAppId, dto.getAppId())
                .like(StrUtil.isNotBlank(dto.getPlatformFileKey()), PlatformFile::getPlatformFileKey, dto.getPlatformFileKey())
                .ge(!Objects.isNull(dto.getStartTime()), PlatformFile::getCreateTime, dto.getStartTime())
                .le(!Objects.isNull(dto.getEndTime()), PlatformFile::getCreateTime, dto.getStartTime())
                .eq(PlatformFile::getGroupId, GroupUtils.getGroupId())
                .orderByDesc(PlatformFile::getCreateTime);
        Page<PlatformFile> platformFilePage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        platformFileMapper.selectPage(platformFilePage, wrapper);
        LocalDateTime minusDays = LocalDateTime.now().minusDays(3L);
        return SearchResponseDto.<PlatformFileSearchResponseDto>builder()
                .records(platformFilePage.getRecords().stream().map(platformFile -> {
                    PlatformFileTypeEnum platformFileTypeEnum = PlatformFileTypeEnum.getInstanceByCode(platformFile.getPlatformFileType());
                    ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(platformFile.getChannelType());
                    if (Objects.isNull(platformFileTypeEnum)) {
                        throw new BusinessException("文件类型不存在");
                    }
                    if (Objects.isNull(channelTypeEnum)) {
                        throw new BusinessException("渠道类型不存在");
                    }
                    PlatformFileSearchResponseDto platformFileSearchResponseDto = new PlatformFileSearchResponseDto();
                    BeanUtil.copyProperties(platformFile, platformFileSearchResponseDto);
                    platformFileSearchResponseDto.setPlatformFileTypeName(platformFileTypeEnum.getName());
                    platformFileSearchResponseDto.setChannelTypeName(channelTypeEnum.getName());
                    platformFileSearchResponseDto.setAppName(channelAppMapper.selectById(platformFile.getAppId()).getAppName());
                    // 企微三天有效期
                    if (StrUtil.equals(ChannelTypeEnum.WECHAT.getCode(), String.valueOf(platformFile.getChannelType()))) {
                        platformFileSearchResponseDto.setPlatformFileStatus(
                                platformFile.getCreateTime().isBefore(minusDays) ? CommonConstant.DISABLE_STATUS : CommonConstant.ENABLE_STATUS
                        );
                    }
                    return platformFileSearchResponseDto;
                }).collect(Collectors.toList()))
                .total(platformFilePage.getTotal())
                .current(platformFilePage.getCurrent())
                .size(platformFilePage.getSize())
                .pages(platformFilePage.getPages()).build();
    }

    @Override
    public void uploadFile(PlatformFileUploadRequestDto dto) throws IOException {
        // 获取文件
        MultipartFile platformFile = dto.getPlatformFile();
        // 获取应用类型枚举
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(dto.getChannelType());
        PlatformFileTypeEnum platformFileTypeEnum = PlatformFileTypeEnum.getInstanceByCode(dto.getPlatformFileType());
        // 获取应用实体
        Long appId = dto.getAppId();
        ChannelApp channelApp = channelAppMapper.selectById(appId);
        if (Objects.isNull(channelApp)) {
            throw new BusinessException("应用不存在");
        }
        // 获取相关文件信息
        String originalFilename = platformFile.getOriginalFilename();
        long fileSize = platformFile.getSize();
        if (StrUtil.isBlank(originalFilename)) {
            throw new BusinessException("上传文件错误");
        }
        String fileFormat = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 设置发送具体平台 DTO
        PlatformFileDto platformFileDto = new PlatformFileDto();
        platformFileDto.setFile(platformFile.getBytes());
        platformFileDto.setFileName(dto.getPlatformFileName() + "." + fileFormat);
        platformFileDto.setFileType(platformFileTypeEnum.getFileType());
        // 设置 PlatformFile 实体
        PlatformFile platformFileEntity = new PlatformFile();
        BeanUtil.copyProperties(dto, platformFileEntity);
        String fileKey = "";
        // 得到应用配置
        String appConfig = aesUtils.decrypt(channelApp.getAppConfig());
        // 具体执行选择
        switch (channelTypeEnum) {
            case DING -> {
                DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);
                String accessToken = dingClient.getAccessToken(dingApp);
                if (PlatformFileTypeEnum.DING_VOICE.getFileType().equals(platformFileTypeEnum.getFileType())) {
                    if (!PlatformFileConstant.DING_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的语音");
                    }
                    if (fileSize > PlatformFileConstant.DING_VOICE_MAX_SIZE) {
                        throw new BusinessException("语音最大为：2M");
                    }
                } else {
                    if (!PlatformFileConstant.DING_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的文件");
                    }
                    if (fileSize > PlatformFileConstant.DING_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：20M");
                    }
                }
                fileKey = dingClient.uploadDingFile(accessToken, platformFileDto);
            }
            case WECHAT -> {
                // 得到 token
                WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);
                String accessToken = weChatClient.getAccessToken(weChatApp);
                // 发送
                if (PlatformFileTypeEnum.WECHAT_IMAGE.getFileType().equals(platformFileTypeEnum.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_IMAGE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的图片");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_IMAGE_MAX_SIZE) {
                        throw new BusinessException("图片最大为：10M");
                    }
                    fileKey = weChatClient.uploadWeChatFile(accessToken, platformFileDto);
                } else if (PlatformFileTypeEnum.WECHAT_VOICE.getFileType().equals(platformFileTypeEnum.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_VOICE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的音频");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_VOICE_MAX_SIZE) {
                        throw new BusinessException("音频最大为：2M");
                    }
                    fileKey = weChatClient.uploadWeChatFile(accessToken, platformFileDto);
                } else if (PlatformFileTypeEnum.WECHAT_VIDEO.getFileType().equals(platformFileTypeEnum.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_VIDEO_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的视频");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_VIDEO_MAX_SIZE) {
                        throw new BusinessException("视频最大为：10M");
                    }
                    fileKey = weChatClient.uploadWeChatFile(accessToken, platformFileDto);
                } else {
                    if (fileSize > PlatformFileConstant.WECHAT_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：20M");
                    }
                    fileKey = weChatClient.uploadWeChatFile(accessToken, platformFileDto);
                }
            }
            case FEI_SHU -> {
                FeiShuApp feiShuApp = JSONUtil.toBean(appConfig, FeiShuApp.class);
                // 得到 token
                String tenantAccessToken = feiShuClient.getTenantAccessToken(feiShuApp);
                // 发送
                if (PlatformFileTypeEnum.FEI_SHU_IMAGE.getFileType().equals(platformFileTypeEnum.getFileType())) {
                    if (!PlatformFileConstant.FEI_SHU_IMAGE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的图片");
                    }
                    if (fileSize > PlatformFileConstant.FEI_SHU_IMAGE_FILE_MAX_SIZE) {
                        throw new BusinessException("图片最大为：10M");
                    }
                    fileKey = feiShuClient.uploadFeiShuImageFile(tenantAccessToken, platformFileDto);
                } else {
                    if (!PlatformFileConstant.FEI_SHU_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的文件");
                    }
                    if (fileSize > PlatformFileConstant.FEI_SHU_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：30M");
                    }
                    fileKey = feiShuClient.uploadFeiShuFile(tenantAccessToken, platformFileDto);
                }
            }
            default -> {
            }
        }
        // 保存入库
        platformFileEntity.setPlatformFileKey(fileKey);
        platformFileEntity.setGroupId(GroupUtils.getGroupId());
        platformFileMapper.insert(platformFileEntity);
    }
}




