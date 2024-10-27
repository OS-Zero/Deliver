package com.oszero.deliver.admin.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.oszero.deliver.admin.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分组表
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@TableName(value = "group")
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseEntity implements Serializable {
    /**
     * groupId
     */
    @TableId(type = IdType.AUTO)
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组描述
     */
    private String groupDescription;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-不删除 1-删除
     */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
