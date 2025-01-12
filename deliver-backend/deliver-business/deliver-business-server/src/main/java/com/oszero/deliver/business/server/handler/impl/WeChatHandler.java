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

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.PushSubjectEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.platformclient.client.wechat.WeChatClient;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.model.app.WeChatApp;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class WeChatHandler extends BaseHandler {

    private final WeChatClient weChatClient;
    private final AppConfigUtils appConfigUtils;

    public WeChatHandler(WeChatClient weChatClient, AppConfigUtils appConfigUtils) {
        this.weChatClient = weChatClient;
        this.appConfigUtils = appConfigUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        String messageType = sendTaskDto.getMessageType();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(messageType);
        if (Objects.isNull(messageTypeEnum)) {
            throw new RuntimeException("消息类型非法");
        }
        String pushSubject = messageTypeEnum.getPushSubject();
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);
        String accessToken = weChatClient.getAccessToken(weChatApp);
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        messageParam.remove(ClientConstant.USER_ID_TYPE);
        if (StrUtil.equals(PushSubjectEnum.WECHAT_APP.getCode(), pushSubject)) {
            weChatClient.sendAppMessage(accessToken, messageParam);
        } else if (StrUtil.equals(PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode(), pushSubject)) {
            weChatClient.sendAppGroupMessage(accessToken, messageParam);
        } else if (StrUtil.equals(PushSubjectEnum.WECHAT_SCHOOL.getCode(), pushSubject)) {
            weChatClient.sendAppSchoolMessage(accessToken, messageParam);
        } else if (StrUtil.equals(PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode(), pushSubject)) {
            weChatClient.sendRobotMessage(messageParam, sendTaskDto.getUsers());
        }
    }
}
