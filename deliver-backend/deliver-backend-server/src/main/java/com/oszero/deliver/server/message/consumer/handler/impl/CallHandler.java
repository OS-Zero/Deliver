package com.oszero.deliver.server.message.consumer.handler.impl;

import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.client.CallClient;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

/**
 * 电话消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class CallHandler extends BaseHandler {

    private final CallClient callClient;
    private final AesUtils aesUtils;

    public CallHandler(CallClient callClient, MessageRecordService messageRecordService, AesUtils aesUtils) {
        this.callClient = callClient;
        this.messageRecordService = messageRecordService;
        this.aesUtils = aesUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
