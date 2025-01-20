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

package com.oszero.deliver.business.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.enums.ChannelProviderTypeEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.common.util.ChannelProviderUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.platformclient.client.sms.SmsClient;
import com.oszero.deliver.platformclient.client.sms.factory.SmsFactory;
import com.oszero.deliver.platformclient.model.app.sms.AliYunSmsApp;
import com.oszero.deliver.platformclient.model.app.sms.SmsApp;
import com.oszero.deliver.platformclient.model.app.sms.TencentSmsApp;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 短信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class SmsHandler extends BaseHandler {

    private final SmsFactory smsFactory;
    private final AppConfigUtils appConfigUtils;

    public SmsHandler(SmsFactory smsFactory, AppConfigUtils appConfigUtils) {
        this.smsFactory = smsFactory;
        this.appConfigUtils = appConfigUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        try {
            String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
            ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(sendTaskDto.getChannelProviderType());
            if (Objects.isNull(channelProviderTypeEnum)) {
                throw new MessageException("没有指定的短信服务");
            }
            SmsApp smsApp = getSmsApp(channelProviderTypeEnum.getName(), appConfig);
            SmsClient smsClient = smsFactory.getClient(channelProviderTypeEnum.getName());
            smsClient.sendSms(smsApp, sendTaskDto.getMessageParam(), sendTaskDto.getUsers());
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    private SmsApp getSmsApp(String smsProvider, String appConfig) {
        if (ChannelProviderUtils.isAliYun(smsProvider)) {
            return JSONUtil.toBean(appConfig, AliYunSmsApp.class);
        } else if (ChannelProviderUtils.isTencent(smsProvider)) {
            return JSONUtil.toBean(appConfig, TencentSmsApp.class);
        }
        throw new MessageException("没有指定的短信服务");
    }
}
