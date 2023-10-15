package com.oszero.deliver.server.enums;

public enum ResultEnum {
    SUCCESS(200, "success"),
    CLIENT_ERROR(400, "client_error"),
    SERVER_ERROR(500, "server_error"),
    ERROR(600, "error");

    private final Integer code;
    private final String message;

    private ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}