package com.oszero.deliver.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 渠道应用信息
 *
 * @author oszero
 * @version 1.0.0
 */
@TableName(value ="app")
@Data
public class App implements Serializable {
    /**
     * appId
     */
    @TableId(type = IdType.AUTO)
    private Long appId;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
     */
    private Integer channelType;

    /**
     * 应用信息配置 json
     */
    private String appConfig;

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
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}