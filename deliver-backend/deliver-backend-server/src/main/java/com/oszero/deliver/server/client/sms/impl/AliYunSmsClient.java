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

package com.oszero.deliver.server.client.sms.impl;

import cn.hutool.core.util.StrUtil;
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

/**
 * 短信客户端阿里云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AliYunSmsClient implements SmsClient {

    private static final String ENDPOINT_OVERRIDE = "dysmsapi.aliyuncs.com";
    private static final String REGION = "region";
    private static final String SIG_NAME = "signName";
    private static final String TEMPLATE_CODE = "templateCode";
    private static final String TEMPLATE_PARAM = "templateParam";
    private static final String SMS_UP_EXTEND_CODE = "smsUpExtendCode";
    private static final String OUT_ID = "outId";

    @Override
    public void sendSms(SmsApp smsApp, SendTaskDto sendTaskDto) {
        try {
            AliYunSmsApp aliYunSmsApp = (AliYunSmsApp) smsApp;

            List<String> users = sendTaskDto.getUsers();
            String phoneNumbers = String.join(",", users);

            Map<String, Object> paramMap = sendTaskDto.getParamMap();

            // 获取阿里云短信各级参数
            String region = paramMap.get(REGION).toString();
            String signName = paramMap.get(SIG_NAME).toString();
            String templateCode = paramMap.get(TEMPLATE_CODE).toString();
            String templateParam = paramMap.getOrDefault(TEMPLATE_PARAM, "").toString();
            String smsUpExtendCode = paramMap.getOrDefault(SMS_UP_EXTEND_CODE, "").toString();
            String outId = paramMap.getOrDefault(OUT_ID, "").toString();

            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId(aliYunSmsApp.getAccessKeyId())
                    .accessKeySecret(aliYunSmsApp.getAccessKeySecret()).build());

            try (AsyncClient client = AsyncClient.builder()
                    .region(region)
                    .credentialsProvider(provider)
                    .overrideConfiguration(ClientOverrideConfiguration
                            .create()
                            .setEndpointOverride(ENDPOINT_OVERRIDE)
                    ).build()) {

                SendSmsRequest.Builder builder = SendSmsRequest.builder()
                        .phoneNumbers(phoneNumbers)
                        .signName(signName)
                        .templateCode(templateCode);

                if (StrUtil.isNotBlank(templateParam)) {
                    builder.templateParam(templateParam);
                }
                if (StrUtil.isNotBlank(smsUpExtendCode)) {
                    builder.smsUpExtendCode(smsUpExtendCode);
                }
                if (StrUtil.isNotBlank(outId)) {
                    builder.outId(outId);
                }

                SendSmsRequest sendSmsRequest = builder.build();

                CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
                SendSmsResponse resp = response.get();
            } catch (Exception e) {
                throw new MessageException(e.getMessage());
            }
        } catch (Exception e) {
            throw new MessageException("阿里云短信发送失败，" + e.getMessage());
        }
    }
}
