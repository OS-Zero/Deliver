package com.oszero.deliver.server.exception;

import lombok.Getter;

/**
 * 前置处理责任链异常
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public class LinkProcessException extends RuntimeException{
    private final String name = "处理消息的前置流程错误：";
    private final String message;

    public LinkProcessException(String message) {
        super(message);
        this.message = name + message;
    }
}
