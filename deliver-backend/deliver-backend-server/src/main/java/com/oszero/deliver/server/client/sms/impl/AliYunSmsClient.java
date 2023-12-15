package com.oszero.deliver.server.client.sms.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.sms.AliYunSmsApp;
import com.oszero.deliver.server.model.app.sms.SmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    private static final String TEMPLATE_PARAM = "templateParam";
    private static final String ENDPOINT_OVERRIDE = "dysmsapi.aliyuncs.com";
    private static final String SMS_UP_EXTEND_CODE = "smsUpExtendCode";
    private static final String OUT_ID = "outId";
    private static final String SIG_NAME = "signName";
    private static final String TEMPLATE_CODE = "templateCode";
    private static final String REGION = "region";

    @Override
    public void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto) {
        List<String> users = sendTaskDto.getUsers();
        String phoneNumbers = String.join(",", users);
        Map<String,Object> map = sendTaskDto.getParamMap();
        AliYunSmsApp aliYunSmsApp = (AliYunSmsApp) smsApp;

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(aliYunSmsApp.getAccessKeyId())
                .accessKeySecret(aliYunSmsApp.getAccessKeySecret())
                .build());

        try (AsyncClient client = AsyncClient.builder()
                .region((String) map.get(REGION))
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration
                                .create()
                                .setEndpointOverride(ENDPOINT_OVERRIDE)
                )
                .build()) {

            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .phoneNumbers(phoneNumbers)
                    .signName((String) map.get(SIG_NAME))
                    .templateCode((String) map.get(TEMPLATE_CODE))
                    .templateParam((String)map.get(TEMPLATE_PARAM))
                    .smsUpExtendCode((String) map.get(SMS_UP_EXTEND_CODE))
                    .outId((String) map.get(OUT_ID))
                    .build();

            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);

            SendSmsResponse resp = response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MessageException("短信发送失败，" + e.getMessage());
        }
    }
}
