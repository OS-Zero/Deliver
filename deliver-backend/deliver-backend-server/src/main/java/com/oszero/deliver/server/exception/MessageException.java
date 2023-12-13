package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.Getter;

/**
 * 消息链路异常
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public class MessageException extends RuntimeException {
    private String message;
    private ResultEnum code;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
        this.message = message;
        this.code = ResultEnum.ERROR;
    }

    public MessageException(SendTaskDto sendTaskDto, String message) {
        super(message);
        this.message = MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, message) + "！！！";
        this.code = ResultEnum.ERROR;
    }

    public MessageException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum;
    }
}
