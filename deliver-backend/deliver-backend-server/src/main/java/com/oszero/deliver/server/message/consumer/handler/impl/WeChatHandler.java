package com.oszero.deliver.server.message.consumer.handler.impl;

import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.SmsUtils;
import com.oszero.deliver.server.util.channel.WeChatUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

/**
 * 企业微信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class WeChatHandler extends BaseHandler {

    private final WeChatUtils weChatUtils;

    public WeChatHandler(WeChatUtils weChatUtils, MessageRecordService messageRecordService) {
        this.weChatUtils = weChatUtils;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
