package com.oszero.deliver.server.handler;

import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者处理器抽象类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
public abstract class BaseHandler {

    protected MessageRecordService messageRecordService;

    /**
     * 消息处理
     *
     * @param sendTaskDto 发送 Dto
     * @throws Exception 异常
     */
    public void doHandle(SendTaskDto sendTaskDto) throws Exception {
        // 1. 前置处理日志
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "消息已达到处理器开始处理");
        // 2. 具体处理
        handle(sendTaskDto);

        // 3. 后置处理 记录消息，消息发送成功
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "消息处理成功，开始记录消息成功记录");

        sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(
                sendTaskDto,
                StatusEnum.ON,
                user
        ));

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "成功完成消息记录，所有发送人已成功接收，本次消息流程已全部顺利完成");
    }

    protected abstract void handle(SendTaskDto sendTaskDto) throws Exception;

}
