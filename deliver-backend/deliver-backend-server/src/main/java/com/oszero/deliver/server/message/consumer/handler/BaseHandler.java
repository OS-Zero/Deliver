package com.oszero.deliver.server.message.consumer.handler;

import com.oszero.deliver.server.model.dto.SendTaskDto;

/**
 * 消费者处理器抽象类
 *
 * @author oszero
 * @version 1.0.0
 */
public abstract class BaseHandler {

    public void doHandle(SendTaskDto sendTaskDto) throws Exception {
        // 1. 前置处理
        // 2. 具体处理
        handle(sendTaskDto);
    }

    protected abstract void handle(SendTaskDto sendTaskDto) throws Exception;

}
