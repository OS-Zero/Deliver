package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;

public class SystemException extends RuntimeException {

    private String message = "";
    private ResultEnum code = ResultEnum.ERROR;

    public SystemException(){
        super();
    }

    public SystemException(String message){
        super(message);
        this.message = message;
    }

    public SystemException(ResultEnum code, String message){
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultEnum getCode() {
        return code;
    }

    public void setCode(ResultEnum code) {
        this.code = code;
    }
}
