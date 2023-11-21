package com.oszero.deliver.admin.model;

import com.oszero.deliver.admin.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用响应
 *
 * @param <T> 泛型
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

    /**
     * 成功响应无参数
     *
     * @param <T> 泛型
     * @return CommonResult
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), null, null);
    }

    /**
     * 成功响应有参数
     *
     * @param <T>  泛型
     * @param data 数据
     * @return CommonResult
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), data, null);
    }

    /**
     * 失败响应无参数
     *
     * @param <T> 泛型
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail() {
        return new CommonResult<>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMessage());
    }

    /**
     * 失败响应枚举
     *
     * @param <T>        泛型
     * @param resultEnum 返回枚举
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(ResultEnum resultEnum) {
        return new CommonResult<>(resultEnum.getCode(), null, resultEnum.getMessage());
    }

    /**
     * 失败响应错误信息
     *
     * @param <T>          泛型
     * @param errorMessage 错误信息
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(String errorMessage) {
        return new CommonResult<>(ResultEnum.ERROR.getCode(), null, errorMessage);
    }
}
