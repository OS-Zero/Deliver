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

package com.oszero.deliver.business.server.pretreatment.link.paramhandle.ding;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.PushSubjectEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.model.app.DingApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DingParamSetting implements MessageLink<SendTaskDto> {

    private final AppConfigUtils appConfigUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(sendTaskDto.getMessageType());
        if (messageTypeEnum == null) {
            throw new MessageException("消息类型非法");
        }
        String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        String pushSubject = messageTypeEnum.getPushSubject();
        String dingUserIdType = messageParam.get(ClientConstant.USER_ID_TYPE).toString();
        List<String> users = sendTaskDto.getUsers();
        if (StrUtil.equals(PushSubjectEnum.DING_WORK_NOTICE.getCode(), pushSubject)) {
            messageParam.put(dingUserIdType, String.join(ClientConstant.SPILT, users));
            messageParam.put(ClientConstant.DING_AGENT_ID, dingApp.getAgentId());
            messageParam.put(ClientConstant.DING_MSG_TYPE, messageTypeEnum.getMsgType());
        } else if (StrUtil.equals(PushSubjectEnum.DING_ROBOT.getCode(), pushSubject)) {
            if (ClientConstant.DING_OPEN_CONVERSATION_ID.equals(dingUserIdType)) {
                messageParam.put(dingUserIdType, users.get(0));
            } else {
                messageParam.put(dingUserIdType, users);
            }
            messageParam.compute(ClientConstant.DING_MSG_PARAM, (k, msgParam) -> JSONUtil.toJsonStr(msgParam));
            messageParam.put(ClientConstant.DING_ROBOT_CODE, dingApp.getRobotCode());
            messageParam.put(ClientConstant.DING_MSG_KEY, messageTypeEnum.getMsgType());
        }
    }
}
