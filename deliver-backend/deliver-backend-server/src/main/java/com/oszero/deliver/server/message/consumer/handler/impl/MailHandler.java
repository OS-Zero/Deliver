package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.client.MailClient;
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
    private final AesUtils aesUtils;

    public MailHandler(MailClient mailClient, MessageRecordService messageRecordService, AesUtils aesUtils) {
        this.mailClient = mailClient;
        this.messageRecordService = messageRecordService;
        this.aesUtils = aesUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
        MailApp mailApp = JSONUtil.toBean(appConfigJson, MailApp.class);

        mailClient.sendMail(mailApp, sendTaskDto);
    }
}
