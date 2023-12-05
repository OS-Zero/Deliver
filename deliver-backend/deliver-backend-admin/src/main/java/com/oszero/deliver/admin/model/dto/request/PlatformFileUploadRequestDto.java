package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

/**
 * 平台文件上传请求 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class PlatformFileUploadRequestDto {

    /**
     * 文件名
     */
    @NotBlank
    private String fileName;

    /**
     * APP 类型（1-钉钉2-企业微信3-飞书）
     */
    @NotNull
    private Integer appType;

    /**
     * 文件类型
     */
    @NotBlank
    private String fileType;

    /**
     * 关联 AppId
     */
    @NotNull
    private Long appId;

    /**
     * 平台文件
     */
    @NotNull
    private MultipartFile platformFile;
}
