package com.oszero.deliver.admin.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 模板查询响应 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateSearchResponseDto {
    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 推送范围（0-不限 1-企业内部 2-外部）
     */
    private Integer pushRange;

    /**
     * 用户类型（1-企业账号 2-电话 3-平台ID 4-邮箱）
     */
    private Integer usersType;

    /**
     * 推送方式
     * {
     * channelType（1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
     * messageType（1-文本 2-钉钉链接 3-钉钉卡片 4-钉钉OA 5-微信卡片 6-微信md 7-飞书md 8-飞书卡片）
     * }
     */
    private String pushWays;

    /**
     * 模板使用数
     */
    private Integer useCount;

    /**
     * 模板状态
     */
    private Integer templateStatus;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;
}
