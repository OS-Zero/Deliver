package com.oszero.deliver.server.client.sms.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.SmsApp;
import com.oszero.deliver.server.model.app.smsapp.AliYunSmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 短信客户端阿里云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AliYunSmsClient implements SmsClient {

    private static  final String Template_Param = "templateParam";
    private static final String Endpoint_Override = "dysmsapi.aliyuncs.com";

    @Override
    public void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto) {
        String s = sendTaskDto.getUsers().toString();
        String phoneNumbers = s.substring(1, s.length()-1);
        AliYunSmsApp aliYunSmsApp = (AliYunSmsApp) smsApp;


        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                // 此处以把AccessKey 和 AccessKeySecret 保存在环境变量为例说明。您也可以根据业务需要，保存到配置文件里
                // 强烈建议不要把 AccessKey 和 AccessKeySecret 保存到代码里，会存在密钥泄漏风险
                .accessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                .accessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"))
                .build());

        AsyncClient client = null;
        try {
            client = AsyncClient.builder()
                    .credentialsProvider(provider)
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    .setEndpointOverride(Endpoint_Override)
                    )
                    .build();


            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .phoneNumbers(phoneNumbers)
                    .signName(aliYunSmsApp.getSignName())
                    .templateCode(aliYunSmsApp.getTemplateCode())
                    .templateParam((String) sendTaskDto.getParamMap().get(Template_Param))
                    .build();


            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);

            SendSmsResponse resp = response.get();
        } catch (InterruptedException e) {
            throw new MessageException("短信发送失败" + e.getMessage());
        } catch (ExecutionException e) {
            throw new MessageException("短信发送失败" + e.getMessage());
        } finally {
            client.close();
        }

    }


}
