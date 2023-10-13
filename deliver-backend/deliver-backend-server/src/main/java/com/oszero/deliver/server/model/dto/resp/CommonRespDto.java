package com.oszero.deliver.server.model.dto.resp;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonRespDto<T> {
    private Integer code;
    private T data;
    private String errorMessage;

    public static <T> CommonRespDto<T> success() {
        return new CommonRespDto<>(ResultEnum.SUCCESS.getCode(), null, null);
    }

    public static <T> CommonRespDto<T> success(T data) {
        return new CommonRespDto<>(ResultEnum.SUCCESS.getCode(), data, null);
    }

    public static <T> CommonRespDto<T> fail() {
        return new CommonRespDto<>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMessage());
    }

    public static <T> CommonRespDto<T> fail(ResultEnum resultEnum) {
        return new CommonRespDto<>(resultEnum.getCode(), null, resultEnum.getMessage());
    }


}
