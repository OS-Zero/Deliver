package com.oszero.deliver.server.handler.impl;

import com.oszero.deliver.server.client.factory.CallFactory;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
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

    private final CallFactory callFactory;

    public CallHandler(CallFactory callFactory, MessageRecordService messageRecordService) {
        this.callFactory = callFactory;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
