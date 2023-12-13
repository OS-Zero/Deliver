package com.oszero.deliver.server.client.sms.impl;

import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.model.app.SmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import org.springframework.stereotype.Service;

/**
 * 短信客户端腾讯云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class TenCentSmsClient implements SmsClient {
    @Override
    public void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto) {
        // TODO: 2023/12/13  接入腾讯云
    }
}
