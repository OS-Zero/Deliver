package com.oszero.deliver.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息记录
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "message_record")
public class MessageRecord implements Serializable {
    /**
     * 消息链路 id
     */
    private String traceId;

    /**
     * 模板 id
     */
    private Long templateId;

    /**
     * appId
     */
    private Long appId;

    /**
     * 消息状态（1-发送成功0-发送失败）
     */
    private Integer messageStatus;

    /**
     * 用户类型（1-企业账号2-手机3-邮箱4-平台userId）
     */
    private Integer userType;

    /**
     * 消息推送人
     */
    private String pushUser;

    /**
     * 发送范围
     */
    private Integer pushRange;

    /**
     * 发送渠道类型
     */
    private Integer channelType;

    /**
     * 消息类型（见 MessageTypeEnum）
     */
    private String messageType;

    /**
     * 是否重试消息（1-是 0-首次发送）
     */
    private Integer retried;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}