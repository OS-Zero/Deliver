package com.oszero.deliver.admin.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * app 查询响应 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class AppSearchResponseDto {
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
     * APP 使用数
     */
    private Integer useCount;

    /**
     * APP 状态
     */
    private Integer appStatus;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
