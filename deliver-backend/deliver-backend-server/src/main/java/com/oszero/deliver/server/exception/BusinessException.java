package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {
    private String message;
    private final ResultEnum code = ResultEnum.ERROR;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
