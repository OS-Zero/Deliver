package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.constant.PlatformFileConstant;
import com.oszero.deliver.admin.enums.AppTypeEnum;
import com.oszero.deliver.admin.enums.PlatformFileTypeEnum;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.mapper.PlatformFileMapper;
import com.oszero.deliver.admin.model.app.DingApp;
import com.oszero.deliver.admin.model.app.FeiShuApp;
import com.oszero.deliver.admin.model.app.WeChatApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.PlatformFile;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.PlatformFileService;
import com.oszero.deliver.admin.util.AesUtils;
import com.oszero.deliver.admin.util.app.DingUtils;
import com.oszero.deliver.admin.util.app.FeiShuUtils;
import com.oszero.deliver.admin.util.app.WeChatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 针对表【platform_file(平台文件表)】的数据库操作Service实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PlatformFileServiceImpl extends ServiceImpl<PlatformFileMapper, PlatformFile>
        implements PlatformFileService {

    private final DingUtils dingUtils;
    private final FeiShuUtils feiShuUtils;
    private final WeChatUtils weChatUtils;
    private final AppService appService;
    private final AesUtils aesUtils;

    @Override
    public void uploadFile(PlatformFileUploadRequestDto dto) throws IOException {
        // 获取文件
        MultipartFile platformFile = dto.getPlatformFile();

        // 获取 APP 类型枚举
        Integer appType = dto.getAppType();
        AppTypeEnum appTypeEnum = AppTypeEnum.getInstanceByCode(appType);
        if (Objects.isNull(appTypeEnum)) {
            throw new BusinessException("appType 错误！！！");
        }

        // 获取 APP 实体
        Long appId = dto.getAppId();
        App app = appService.getById(appId);
        if (Objects.isNull(app)) {
            throw new BusinessException("appId 不存在！！！");
        }

        // 获取相关文件信息
        String originalFilename = platformFile.getOriginalFilename();
        long fileSize = platformFile.getSize();
        if (StrUtil.isBlank(originalFilename)) {
            throw new BusinessException("上传文件错误！！！");
        }
        String fileFormat = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        // 设置发送具体平台 DTO
        PlatformFileDto platformFileDto = new PlatformFileDto();
        platformFileDto.setFile(platformFile.getBytes());
        platformFileDto.setFileName(dto.getFileName() + "." + fileFormat);
        platformFileDto.setFileType(dto.getFileType());

        // 设置 PlatformFile 实体
        PlatformFile platformFileEntity = new PlatformFile();
        platformFileEntity.setFileName(dto.getFileName());
        platformFileEntity.setFileType(dto.getAppType() + "-" + dto.getFileType());
        platformFileEntity.setAppType(dto.getAppType());
        platformFileEntity.setAppId(dto.getAppId());
        String fileKey = "";

        // 得到 APP 配置
        String appConfig = aesUtils.decrypt(app.getAppConfig());
        // 具体执行选择
        switch (appTypeEnum) {
            case DING: {

                DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);
                String accessToken = dingUtils.getAccessToken(dingApp);

                if (PlatformFileTypeEnum.DING_VOICE.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.DING_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的语音！！！");
                    }
                    if (fileSize > PlatformFileConstant.DING_VOICE_MAX_SIZE) {
                        throw new BusinessException("语音最大为：2M！！！");
                    }
                } else {
                    if (!PlatformFileConstant.DING_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的文件！！！");
                    }
                    if (fileSize > PlatformFileConstant.DING_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：20M！！！");
                    }
                }
                fileKey = dingUtils.uploadDingFile(accessToken, platformFileDto);
                break;
            }
            case WECHAT: {
                // 得到 token
                WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);
                String accessToken = weChatUtils.getAccessToken(weChatApp);

                // 发送
                if (PlatformFileTypeEnum.WECHAT_IMAGE.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_IMAGE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的图片！！！");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_IMAGE_MAX_SIZE) {
                        throw new BusinessException("图片最大为：10M！！！");
                    }
                    fileKey = weChatUtils.uploadWeChatFile(accessToken, platformFileDto);
                } else if (PlatformFileTypeEnum.WECHAT_VOICE.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_VOICE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的音频！！！");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_VOICE_MAX_SIZE) {
                        throw new BusinessException("音频最大为：2M！！！");
                    }
                    fileKey = weChatUtils.uploadWeChatFile(accessToken, platformFileDto);
                } else if (PlatformFileTypeEnum.WECHAT_VIDEO.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.WECHAT_VIDEO_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的视频！！！");
                    }
                    if (fileSize > PlatformFileConstant.WECHAT_VIDEO_MAX_SIZE) {
                        throw new BusinessException("视频最大为：10M！！！");
                    }
                    fileKey = weChatUtils.uploadWeChatFile(accessToken, platformFileDto);
                } else {
                    if (fileSize > PlatformFileConstant.WECHAT_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：20M！！！");
                    }
                    fileKey = weChatUtils.uploadWeChatFile(accessToken, platformFileDto);
                }
                break;
            }
            case FEI_SHU: {
                FeiShuApp feiShuApp = JSONUtil.toBean(appConfig, FeiShuApp.class);
                // 得到 token
                String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
                // 发送
                if (PlatformFileTypeEnum.FEI_SHU_IMAGE.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.FEI_SHU_IMAGE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的图片！！！");
                    }
                    if (fileSize > PlatformFileConstant.FEI_SHU_IMAGE_FILE_MAX_SIZE) {
                        throw new BusinessException("图片最大为：10M！！！");
                    }
                    fileKey = feiShuUtils.uploadFeiShuImageFile(tenantAccessToken, platformFileDto);
                } else {
                    if (!PlatformFileConstant.FEI_SHU_FILE_FORMAT_SET.contains(fileFormat.toLowerCase(Locale.ROOT))) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的文件！！！");
                    }
                    if (fileSize > PlatformFileConstant.FEI_SHU_FILE_MAX_SIZE) {
                        throw new BusinessException("文件最大为：30M！！！");
                    }
                    fileKey = feiShuUtils.uploadFeiShuFile(tenantAccessToken, platformFileDto);
                }
                break;
            }
            default: {
            }
        }

        // 保存入库
        platformFileEntity.setFileKey(fileKey);
        this.save(platformFileEntity);
    }

    @Override
    public Page<PlatformFileSearchResponseDto> getPagePlatformFile(PlatformFileSearchRequestDto dto) {
        LambdaQueryWrapper<PlatformFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getFileName()), PlatformFile::getFileName, dto.getFileName())
                .eq(!Objects.isNull(dto.getAppType()), PlatformFile::getAppType, dto.getAppType())
                .like(StrUtil.isNotBlank(dto.getFileType()), PlatformFile::getFileType, dto.getFileType())
                .like(StrUtil.isNotBlank(dto.getFileKey()), PlatformFile::getFileKey, dto.getFileKey())
                .eq(!Objects.isNull(dto.getAppId()), PlatformFile::getAppId, dto.getAppId())
                .ge(!Objects.isNull(dto.getStartTime()), PlatformFile::getCreateTime, dto.getStartTime())
                .le(!Objects.isNull(dto.getEndTime()), PlatformFile::getCreateTime, dto.getStartTime())
                .orderByDesc(PlatformFile::getCreateTime);
        Page<PlatformFile> platformFilePage = new Page<>(dto.getCurrentPage(), dto.getPageSize());

        this.page(platformFilePage, wrapper);

        Page<PlatformFileSearchResponseDto> platformFileSearchResponseDtoPage = new Page<>(platformFilePage.getPages(), platformFilePage.getSize());
        platformFileSearchResponseDtoPage.setRecords(platformFilePage.getRecords().stream().map(platformFile -> {
            PlatformFileSearchResponseDto platformFileSearchResponseDto = new PlatformFileSearchResponseDto();
            BeanUtil.copyProperties(platformFile, platformFileSearchResponseDto);
            platformFileSearchResponseDto.setFileType(PlatformFileConstant.FILE_TYPE_NAME_MAP.get(platformFile.getFileType()));
            return platformFileSearchResponseDto;
        }).collect(Collectors.toList()));

        platformFileSearchResponseDtoPage.setTotal(platformFilePage.getTotal());

        return platformFileSearchResponseDtoPage;
    }
}




