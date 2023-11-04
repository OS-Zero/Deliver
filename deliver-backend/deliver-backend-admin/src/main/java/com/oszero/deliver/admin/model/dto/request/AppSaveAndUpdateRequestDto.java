package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

@Data
public class AppSaveAndUpdateRequestDto {

    /**
     * appId
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
     */
    private Integer channelType;

    /**
     * 应用信息配置 json
     */
    private String appConfig;

    /**
     * APP 状态
     */
    private Integer appStatus;
}
