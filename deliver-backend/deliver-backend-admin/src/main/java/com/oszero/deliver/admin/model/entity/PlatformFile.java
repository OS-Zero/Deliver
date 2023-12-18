package com.oszero.deliver.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 平台文件表
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@TableName(value = "platform_file")
public class PlatformFile implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * APP 类型（1-钉钉2-企业微信3-飞书）
     */
    private Integer appType;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件平台 Key
     */
    private String fileKey;

    /**
     * 文件平台状态（1-生效0-失效）
     */
    private Integer fileStatus;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 关联 AppId
     */
    private Long appId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}