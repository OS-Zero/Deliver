package com.oszero.deliver.server.message.consumer.handler.impl;

import com.oszero.deliver.server.client.SmsClient;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

/**
 * 短信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class SmsHandler extends BaseHandler {

    private final SmsClient smsClient;

    public SmsHandler(SmsClient smsClient, MessageRecordService messageRecordService) {
        this.smsClient = smsClient;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
