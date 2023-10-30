package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 邮箱消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class MailHandler extends BaseHandler {

    private final MailUtils mailUtils;

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = sendTaskDto.getAppConfigJson();
        MailApp mailApp = JSONUtil.toBean(appConfigJson, MailApp.class);
        mailUtils.sendMail(mailApp, sendTaskDto);
    }
}
