package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.constant.PlatformFileConstant;
import com.oszero.deliver.admin.enums.AppTypeEnum;
import com.oszero.deliver.admin.enums.PlatformFileTypeEnum;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.mapper.PlatformFileMapper;
import com.oszero.deliver.admin.model.app.DingApp;
import com.oszero.deliver.admin.model.app.FeiShuApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.PlatformFile;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.PlatformFileService;
import com.oszero.deliver.admin.util.app.DingUtils;
import com.oszero.deliver.admin.util.app.FeiShuUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @author 23088
 * @description 针对表【platform_file(平台文件表)】的数据库操作Service实现
 * @createDate 2023-11-14 19:38:02
 */
@Service
@RequiredArgsConstructor
public class PlatformFileServiceImpl extends ServiceImpl<PlatformFileMapper, PlatformFile>
        implements PlatformFileService {

    private final DingUtils dingUtils;
    private final FeiShuUtils feiShuUtils;
    private final AppService appService;

    @Override
    public void uploadFile(PlatformFileUploadRequestDto dto, MultipartFile platformFile) throws IOException {
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
        platformFileEntity.setFileType(dto.getFileType());
        platformFileEntity.setAppType(dto.getAppType());
        platformFileEntity.setAppId(dto.getAppId());
        String fileKey = "";

        // 具体执行选择
        switch (appTypeEnum) {
            case DING: {
                String appConfig = app.getAppConfig();
                DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);
                String accessToken = dingUtils.getAccessToken(dingApp);
                break;
            }
            case WECHAT: {
                break;
            }
            case FEI_SHU: {
                // 得到 APP 配置
                String appConfig = app.getAppConfig();
                FeiShuApp feiShuApp = JSONUtil.toBean(appConfig, FeiShuApp.class);
                // 得到 token
                String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
                // 发送
                if (PlatformFileTypeEnum.FEI_SHU_IMAGE.getFileType().equals(dto.getFileType())) {
                    if (!PlatformFileConstant.feiShuImageFormatSet.contains(fileFormat)) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的图片！！！");
                    }
                    if (fileSize > PlatformFileConstant.feiShuImageFileMaxSize) {
                        throw new BusinessException("图片最大为：10M！！！");
                    }
                    fileKey = feiShuUtils.uploadFeiShuImageFile(tenantAccessToken, platformFileDto);
                } else {
                    if (!PlatformFileConstant.feiShuFileFormatSet.contains(fileFormat)) {
                        throw new BusinessException("不支持 " + fileFormat + " 格式的文件！！！");
                    }
                    if (fileSize > PlatformFileConstant.feiShuFileMaxSize) {
                        throw new BusinessException("文件最大为：30M！！！");
                    }
                    fileKey = feiShuUtils.uploadFeiShuFile(tenantAccessToken, platformFileDto);
                }
                break;
            }
        }

        // 保存入库
        platformFileEntity.setFileKey(fileKey);
        this.save(platformFileEntity);
    }
}




