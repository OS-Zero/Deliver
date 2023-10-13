package com.oszero.deliver.server.enums;

public enum ResultEnum {
    SUCCESS(0, "success"),
    ERROR(10000000, "error"),
    UNEXPECTED_ERROR(10001000, "服务发生非预期异常，请联系管理员"),
    PARAM_VALIDATED_UN_PASS(10002000, "参数校验(JSR303)不通过，请检查参数或联系管理员"),
    MISSING_SERVLET_REQUEST_PARAMETER_ERROR(10002001, "Query请求参数校验不通过，请检查参数或联系管理员"),
    METHOD_ARGUMENT_TYPE_MISMATCH_ERROR(10002002, "方法请求参数类型不匹配，请检查参数或联系管理员"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR(10002003, "不支持的请求方法，请检查 API 或联系管理员"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR(10002004, "不支持的互联网媒体类型，请检查 API 或联系管理员"),
    AUTHENTICATION(10004001, "很抱歉, 用户认证失效"),
    AUTHORIZATION(10004003, "很抱歉, 暂无权限操作");

    private Integer code;
    private String message;

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