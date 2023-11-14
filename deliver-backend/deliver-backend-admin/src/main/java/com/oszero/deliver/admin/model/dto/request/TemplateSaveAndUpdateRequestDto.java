package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 模板保存以及更新请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateSaveAndUpdateRequestDto {

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 模板名称
     */
    @NotBlank
    private String templateName;

    /**
     * 推送范围（0-不限 1-企业内部 2-外部）
     */
    @NotNull
    private Integer pushRange;

    /**
     * 用户类型（1-企业账号 2-电话 3-平台ID 4-邮箱）
     */
    @NotNull
    private Integer usersType;

    /**
     * 推送方式
     * {
     * "channelType":（1-打电话 2-发短信 3-邮件 4-钉钉 5-企业微信 6-飞书）
     * "messageType": 所见 MessageTypeEnum
     * }
     */
    @NotBlank
    private String pushWays;

    /**
     * 模板状态
     */
    @NotNull
    private Integer templateStatus;

    /**
     * appId
     */
    @NotNull
    private Long appId;
}
