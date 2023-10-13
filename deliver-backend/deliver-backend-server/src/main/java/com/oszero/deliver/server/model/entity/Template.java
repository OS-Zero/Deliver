package com.oszero.deliver.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 消息模板
 * @TableName template
 */
@TableName(value ="template")
@Data
public class Template implements Serializable {
    /**
     * 模板id
     */
    @TableId(type = IdType.AUTO)
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
【
channelType（1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
messageType（1-文本 2-钉钉链接 3-钉钉卡片 4-钉钉OA 5-微信卡片 6-微信md 7-飞书md 8-飞书卡片）
】
     */
    private String pushWays;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除：0-不删除 1-删除
     */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}