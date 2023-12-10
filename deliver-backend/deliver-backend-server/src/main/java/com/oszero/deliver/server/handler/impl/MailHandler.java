package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.MailClient;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

/**
 * 邮箱消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class MailHandler extends BaseHandler {

    private final MailClient mailClient;

    public MailHandler(MailClient mailClient, MessageRecordService messageRecordService) {
        this.mailClient = mailClient;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = sendTaskDto.getAppConfig();
        MailApp mailApp = JSONUtil.toBean(appConfigJson, MailApp.class);

        mailClient.sendMail(mailApp, sendTaskDto);
    }
}
