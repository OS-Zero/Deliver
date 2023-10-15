package com.oszero.deliver.server.exception;

import lombok.Getter;

@Getter
public class PipelineProcessException extends RuntimeException{
    private final String name = "处理消息的前置流程错误：";
    private final String message;

    public PipelineProcessException(String message) {
        super(message);
        this.message = name + message;
    }
}
