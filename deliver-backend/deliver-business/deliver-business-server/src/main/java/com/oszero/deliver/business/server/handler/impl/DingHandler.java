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
import com.oszero.deliver.platformclient.client.ding.DingClient;
import com.oszero.deliver.platformclient.model.app.DingApp;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class DingHandler extends BaseHandler {

    private final DingClient dingClient;
    private final AppConfigUtils appConfigUtils;

    public DingHandler(DingClient dingClient, AppConfigUtils appConfigUtils) {
        this.dingClient = dingClient;
        this.appConfigUtils = appConfigUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String messageType = sendTaskDto.getMessageType();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(messageType);
        if (Objects.isNull(messageTypeEnum)) {
            throw new Exception("消息类型非法");
        }
        String pushSubject = messageTypeEnum.getPushSubject();
        String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);
        String accessToken = dingClient.getAccessToken(dingApp);
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        if (StrUtil.equals(PushSubjectEnum.DING_WORK_NOTICE.getCode(), pushSubject)) {
            dingClient.sendWorkNoticeMessage(accessToken, messageParam);
        } else if (StrUtil.equals(PushSubjectEnum.DING_ROBOT.getCode(), pushSubject)){
            dingClient.sendRobotMessage(accessToken, messageParam);
        }
    }
}
