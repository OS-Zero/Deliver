package com.oszero.deliver.server.client.sms.factory;

import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.client.sms.impl.AliYunSmsClient;
import com.oszero.deliver.server.client.sms.impl.TencentSmsClient;
import com.oszero.deliver.server.enums.SmsProviderTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * SmsFactory
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SmsFactory {

    private final AliYunSmsClient aliYunSmsClient;
    private final TencentSmsClient tencentSmsClient;

    public SmsClient getClient(String smsProvider) {
        if (SmsProviderTypeEnum.ALI_YUN.getName().equals(smsProvider)) {
            return aliYunSmsClient;
        } else if (SmsProviderTypeEnum.TENCENT.getName().equals(smsProvider)) {
            return tencentSmsClient;
        } else {
            throw new MessageException("暂时不提供指定短信服务提供商：" + smsProvider);
        }
    }
}
