package com.oszero.deliver.server.model;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用响应
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class CommonResult<T> {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 成功响应数据
     */
    private T data;

    /**
     * 错误响应信息
     */
    private String errorMessage;

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), null, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), data, null);
    }

    public static <T> CommonResult<T> fail() {
        return new CommonResult<>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMessage());
    }

    public static <T> CommonResult<T> fail(ResultEnum resultEnum) {
        return new CommonResult<>(resultEnum.getCode(), null, resultEnum.getMessage());
    }

    public static <T> CommonResult<T> fail(String errorMessage) {
        return new CommonResult<>(ResultEnum.ERROR.getCode(), null, errorMessage);
    }


}
