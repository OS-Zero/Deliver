package com.oszero.deliver.admin.model.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库通用字段类
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class BaseEntity {
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createUser;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateUser;
}
