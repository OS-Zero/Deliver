package com.oszero.deliver.server.message.consumer.handler.impl;

import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import org.springframework.stereotype.Component;

/**
 * 短信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class SmsHandler extends BaseHandler {
    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
