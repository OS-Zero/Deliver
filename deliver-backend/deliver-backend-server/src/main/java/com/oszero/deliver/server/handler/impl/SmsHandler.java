package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.client.sms.factory.SmsFactory;
import com.oszero.deliver.server.enums.SmsProviderTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.sms.SmsApp;
import com.oszero.deliver.server.model.app.sms.AliYunSmsApp;
import com.oszero.deliver.server.model.app.sms.TencentSmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class SmsHandler extends BaseHandler {

    private static final String SMS_PROVIDER = "smsProvider";

    private final SmsFactory smsFactory;

    public SmsHandler(SmsFactory smsFactory, MessageRecordService messageRecordService) {
        this.smsFactory = smsFactory;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        try {
            Map<String, Object> map = sendTaskDto.getParamMap();
            String appConfig = sendTaskDto.getAppConfig();
            String smsProvider = map.get(SMS_PROVIDER).toString();
            SmsApp smsApp = getSmsApp(smsProvider, appConfig);
            SmsClient smsClient = smsFactory.getClient(smsProvider);
            smsClient.sendSms(smsApp, sendTaskDto);
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, e.getMessage());
        }
    }

    private SmsApp getSmsApp(String smsProvider, String appConfig) {

        if (SmsProviderTypeEnum.ALI_YUN.getName().equals(smsProvider)) {
            return JSONUtil.toBean(appConfig, AliYunSmsApp.class);
        } else if (SmsProviderTypeEnum.TENCENT.getName().equals(smsProvider)) {
            return JSONUtil.toBean(appConfig, TencentSmsApp.class);
        }
        throw new MessageException("没有指定的短信服务 App：" + smsProvider);
    }
}
