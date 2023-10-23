package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.Getter;

/**
 * 系统异常
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public class SystemException extends RuntimeException {

    private String message;
    private final ResultEnum code = ResultEnum.ERROR;

    public SystemException(){
        super();
    }

    public SystemException(String message){
        super(message);
        this.message = message;
    }
}
