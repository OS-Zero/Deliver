package com.oszero.deliver.server.client.sms.factory;

import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.client.sms.impl.AliYunSmsClient;
import com.oszero.deliver.server.client.sms.impl.TenCentSmsClient;
import com.oszero.deliver.server.enums.SmsProvideTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SmsFactory {

    private  final AliYunSmsClient aliYunSmsClient;
    private final TenCentSmsClient tenCentSmsClient;

    public  SmsClient getClient(String smsProvide){
        if(SmsProvideTypeEnum.aliyun.getMessage().equals(smsProvide)){
            return aliYunSmsClient;
        }else if(SmsProvideTypeEnum.tencent.getMessage().equals(smsProvide)){
            return tenCentSmsClient;
        }else {
            throw new MessageException("暂时不提供指定短信服务提供商:"+smsProvide);
        }
    }
}
