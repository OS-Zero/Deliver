package com.oszero.deliver.server.client.sms.impl;

import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.sms.SmsApp;
import com.oszero.deliver.server.model.app.sms.TencentSmsApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 短信客户端腾讯云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
public class TencentSmsClient implements SmsClient {
    @Override
    public void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto) {
        try {
            TencentSmsApp tencentSmsApp = (TencentSmsApp) smsApp;
            String secretId = tencentSmsApp.getSecretId();
            String secretKey = tencentSmsApp.getSecretKey();

            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String region = paramMap.get("region").toString();
            String smsSdkAppId = paramMap.get("smsSdkAppId").toString();
            String signName = paramMap.get("signName").toString();
            String templateId = paramMap.get("templateId").toString();
            List<String> templateParam = (List<String>) paramMap.get("templateParam");

            List<String> users = sendTaskDto.getUsers();

            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            com.tencentcloudapi.sms.v20210111.SmsClient client =
                    new com.tencentcloudapi.sms.v20210111.SmsClient(cred, region, clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumbers = users.toArray(new String[0]);
            req.setPhoneNumberSet(phoneNumbers);

            req.setSmsSdkAppId(smsSdkAppId);
            req.setSignName(signName);
            req.setTemplateId(templateId);

            String[] templateParamSet = templateParam.toArray(new String[0]);
            req.setTemplateParamSet(templateParamSet);

            SendSmsResponse resp = client.SendSms(req);

            log.info("发送腾讯云短信成功");
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "发送腾讯云短信失败，" + e.getMessage());
        }
    }
}
