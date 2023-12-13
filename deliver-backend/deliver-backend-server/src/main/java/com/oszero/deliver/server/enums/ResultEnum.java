package com.oszero.deliver.server.enums;

import lombok.Getter;

/**
 * 响应状态枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public enum ResultEnum {
    SUCCESS(200, "success"),
    CLIENT_ERROR(400, "client_error"),
    SERVER_ERROR(500, "server_error"),
    ERROR(600, "error"),
    REFRESH_TOKEN(700, "refresh_token");
    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}