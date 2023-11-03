package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.util.channel.MailUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 邮箱消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class MailHandler extends BaseHandler {

    private final MailUtils mailUtils;

    public MailHandler(MailUtils mailUtils, MessageRecordService messageRecordService) {
        this.mailUtils = mailUtils;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = sendTaskDto.getAppConfigJson();
        MailApp mailApp = JSONUtil.toBean(appConfigJson, MailApp.class);
        mailUtils.sendMail(mailApp, sendTaskDto);
    }
}
