package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import lombok.Getter;

/**
 * 消息链路异常
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public class MessageException extends RuntimeException {
    private final String name = "消息流程处理错误，消息传递失败，";
    private String message;
    private final ResultEnum code = ResultEnum.ERROR;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
        this.message = name + message;
    }
}
