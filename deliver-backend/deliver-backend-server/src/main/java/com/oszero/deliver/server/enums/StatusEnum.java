package com.oszero.deliver.server.enums;

/**
 * 状态枚举
 *
 * @author oszero
 * @version 1.0.0
 */
public enum StatusEnum {
    ON(1, "启用"),
    OFF(0, "禁用");

    private final Integer status;
    private final String message;

    StatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }
}
