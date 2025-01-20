/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.platformclient.client.sms.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.platformclient.client.sms.SmsClient;
import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.sms.SmsApp;
import com.oszero.deliver.platformclient.model.app.sms.TencentSmsApp;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public void sendSms(SmsApp smsApp, Map<String, Object> paramMap, List<String> users) {
        try {
            // 获取腾讯云API接口安全配置参数
            TencentSmsApp tencentSmsApp = (TencentSmsApp) smsApp;
            String secretId = tencentSmsApp.getSecretId();
            String secretKey = tencentSmsApp.getSecretKey();
            // 获取短信相关参数
            final String region = paramMap.get("region").toString();
            final String smsSdkAppId = paramMap.get("smsSdkAppId").toString();
            final String signName = paramMap.get("signName").toString();
            final String templateId = paramMap.get("templateId").toString();
            final List<String> templateParam = (List<String>) paramMap.getOrDefault("templateParam", new ArrayList<String>());
            final String extendCode = paramMap.getOrDefault("extendCode", "").toString();
            final String sessionContext = paramMap.getOrDefault("sessionContext", "").toString();
            final String senderId = paramMap.getOrDefault("senderId", "").toString();
            // 得到发送人
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            final com.tencentcloudapi.sms.v20210111.SmsClient client =
                    new com.tencentcloudapi.sms.v20210111.SmsClient(cred, region, clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumbers = users.toArray(new String[0]);
            req.setPhoneNumberSet(phoneNumbers);
            req.setSmsSdkAppId(smsSdkAppId);
            req.setSignName(signName);
            req.setTemplateId(templateId);
            if (CollectionUtil.isNotEmpty(templateParam)) {
                String[] templateParamSet = templateParam.toArray(new String[0]);
                req.setTemplateParamSet(templateParamSet);
            }
            if (StrUtil.isNotBlank(extendCode)) {
                req.setExtendCode(extendCode);
            }
            if (StrUtil.isNotBlank(sessionContext)) {
                req.setSessionContext(sessionContext);
            }
            if (StrUtil.isNotBlank(senderId)) {
                req.setSenderId(senderId);
            }
            SendSmsResponse resp = client.SendSms(req);
            SendStatus[] sendStatusSet = resp.getSendStatusSet();
            // 响应判断
            for (SendStatus sendStatus : sendStatusSet) {
                if (!Objects.equals("Ok", sendStatus.getCode())) {
                    throw new ClientException(sendStatus.getMessage());
                }
            }
        } catch (Exception e) {
            throw new ClientException("发送腾讯云短信失败，" + e.getMessage());
        }
    }
}
