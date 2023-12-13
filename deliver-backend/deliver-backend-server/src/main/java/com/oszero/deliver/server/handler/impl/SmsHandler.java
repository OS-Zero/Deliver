package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.client.sms.factory.SmsFactory;
import com.oszero.deliver.server.enums.SmsProvideTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.SmsApp;
import com.oszero.deliver.server.model.app.smsapp.AliYunSmsApp;
import com.oszero.deliver.server.model.app.smsapp.TenCentSmsApp;
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
    private static final String SMS_Provide = "smsProvide";

    private final SmsFactory smsFactory;

    public SmsHandler(SmsFactory smsFactory, MessageRecordService messageRecordService) {
        this.smsFactory = smsFactory;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
       Map<String,Object> map= sendTaskDto.getParamMap();
        String appConfig = sendTaskDto.getAppConfig();
        String sms = (String) map.get(SMS_Provide);
        SmsApp rightProvideApp = getRightProvideApp(sms,appConfig);
        SmsClient smsProvide = smsFactory.getClient(sms);
        smsProvide.sendSms(rightProvideApp,sendTaskDto);
    }

    private SmsApp getRightProvideApp(String sms,String appConfig ){
        for(SmsProvideTypeEnum item: SmsProvideTypeEnum.values()){
            if(item.getMessage().equals(sms)){
              if(SmsProvideTypeEnum.aliyun.getMessage().equals(sms)){
                  return  JSONUtil.toBean(appConfig, AliYunSmsApp.class);
              }else if(SmsProvideTypeEnum.tencent.getMessage().equals(sms)){
                  return  JSONUtil.toBean(appConfig, TenCentSmsApp.class);
              }
            }
        }
        throw new MessageException("没有指定的短信服务App:"+sms);
    }
}
