package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 响应状态枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200, "success"),
    CLIENT_ERROR(400, "client_error"),
    SERVER_ERROR(500, "server_error"),
    ERROR(600, "error");

    private final Integer code;
    private final String message;
}