package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

@Data
public class TemplateSaveAndUpdateRequestDto {

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
     * "channelType":（1-打电话 2-发短信 3-邮件 4-钉钉 5-企业微信 6-飞书）
     * "messageType": 所见 MessageTypeEnum
     * }
     */
    private String pushWays;

    /**
     * 模板状态
     */
    private Integer templateStatus;

    /**
     * appId
     */
    private Long appId;
}
