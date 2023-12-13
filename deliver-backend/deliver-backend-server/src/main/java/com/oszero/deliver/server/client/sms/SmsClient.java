package com.oszero.deliver.server.client.sms;

import com.oszero.deliver.server.model.app.SmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 短信客户端接口
 *
 * @author oszero
 * @version 1.0.0
 */
public interface SmsClient {

    void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto);

}
