package com.oszero.deliver.server.message.consumer.handler;

import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;

/**
 * 消费者处理器抽象类
 *
 * @author oszero
 * @version 1.0.0
 */
public abstract class BaseHandler {

    protected MessageRecordService messageRecordService;

    public void doHandle(SendTaskDto sendTaskDto) throws Exception {
        // 1. todo:前置处理 限流 日志
        // 2. 具体处理
        handle(sendTaskDto);

        // 3. 后置处理 记录消息，消息发送成功
        sendTaskDto.getUsers()
                .forEach(user ->
                        messageRecordService.saveMessageRecord(
                                sendTaskDto,
                                StatusEnum.ON,
                                user
                        ));
    }

    protected abstract void handle(SendTaskDto sendTaskDto) throws Exception;

}
