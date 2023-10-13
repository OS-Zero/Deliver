package com.oszero.deliver.server.model;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private T data;
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


}
