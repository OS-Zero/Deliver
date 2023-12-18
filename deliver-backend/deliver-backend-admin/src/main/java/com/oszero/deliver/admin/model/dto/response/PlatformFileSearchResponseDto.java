package com.oszero.deliver.admin.model.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 平台文件分页搜索响应 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class PlatformFileSearchResponseDto {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 关联 AppId
     */
    private Long appId;
}
