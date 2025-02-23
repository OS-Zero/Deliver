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
import com.oszero.deliver.platformclient.client.call.CallClient;
import com.oszero.deliver.platformclient.client.call.factory.CallFactory;
import com.oszero.deliver.platformclient.model.app.call.AliYunCallApp;
import com.oszero.deliver.platformclient.model.app.call.CallApp;
import com.oszero.deliver.platformclient.model.app.call.TencentCallApp;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class CallHandler extends BaseHandler {

    private final CallFactory callFactory;
    private final AppConfigUtils appConfigUtils;

    public CallHandler(CallFactory callFactory, AppConfigUtils appConfigUtils) {
        this.callFactory = callFactory;
        this.appConfigUtils = appConfigUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        try {
            Map<String, Object> messageParam = sendTaskDto.getMessageParam();
            String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
            Integer channelProviderType = sendTaskDto.getChannelProviderType();
            ChannelProviderTypeEnum channelProviderTypeEnum = ChannelProviderTypeEnum.getInstanceByCode(channelProviderType);
            if (Objects.isNull(channelProviderTypeEnum)) {
                throw new MessageException("没有指定的电话服务");
            }
            CallApp callApp = getCallApp(channelProviderTypeEnum.getCode(), appConfig);
            CallClient callClient = callFactory.getClient(channelProviderTypeEnum.getName());
            callClient.sendCall(callApp, messageParam, sendTaskDto.getUsers());
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    private CallApp getCallApp(String callProvider, String appConfig) {
        if (ChannelProviderUtils.isAliYun(callProvider)) {
            return JSONUtil.toBean(appConfig, AliYunCallApp.class);
        } else if (ChannelProviderUtils.isTencent(callProvider)) {
            return JSONUtil.toBean(appConfig, TencentCallApp.class);
        }
        throw new MessageException("没有指定的电话服务");
    }
}
