package com.oszero.deliver.admin.model.dto.request;

import com.oszero.deliver.admin.annotation.JsonString;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;

/**
 * APP 保存和更新 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class AppSaveAndUpdateRequestDto {

    /**
     * appId
     */
    private Long appId;

    /**
     * 应用名称
     */
    @NotBlank
    @Length(min = 3, max = 20)
    private String appName;

    /**
     * 消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
     */
    @NotNull
    private Integer channelType;

    /**
     * 应用信息配置 json
     */
    @NotBlank
    @JsonString
    private String appConfig;

    /**
     * APP 状态
     */
    @NotNull
    private Integer appStatus;
}
