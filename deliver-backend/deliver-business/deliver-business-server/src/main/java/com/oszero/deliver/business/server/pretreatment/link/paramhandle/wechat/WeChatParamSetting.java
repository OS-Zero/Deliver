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

package com.oszero.deliver.business.server.pretreatment.link.paramhandle.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.PushSubjectEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.model.app.WeChatApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeChatParamSetting implements MessageLink<SendTaskDto> {

    private final AppConfigUtils appConfigUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);
        String messageType = sendTaskDto.getMessageType();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(messageType);
        if (Objects.isNull(messageTypeEnum)) {
            throw new RuntimeException("消息类型非法");
        }
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        String pushSubject = messageTypeEnum.getPushSubject();
        String wechatUserIdType = messageParam.get(ClientConstant.USER_ID_TYPE).toString();
        List<String> users = sendTaskDto.getUsers();
        if (StrUtil.equals(pushSubject, PushSubjectEnum.WECHAT_APP.getCode())) {
            messageParam.put(ClientConstant.WECHAT_AGENT_ID, weChatApp.getAgentid());
            messageParam.put(wechatUserIdType, String.join(ClientConstant.WECHAT_SPILT, users));
        } else if (StrUtil.equals(pushSubject, PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode())) {
            messageParam.put(wechatUserIdType, users.get(0));
        } else if (StrUtil.equals(pushSubject, PushSubjectEnum.WECHAT_SCHOOL.getCode())) {
            messageParam.put(wechatUserIdType, users);
        }
        messageParam.put(ClientConstant.WECHAT_MSG_TYPE, messageTypeEnum.getMsgType());
    }
}
