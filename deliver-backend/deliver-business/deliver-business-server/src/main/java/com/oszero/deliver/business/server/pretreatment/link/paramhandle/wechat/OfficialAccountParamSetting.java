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
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.PushSubjectEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.platformclient.common.ClientConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class OfficialAccountParamSetting implements MessageLink<SendTaskDto> {

    private final AppConfigUtils appConfigUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(messageType);
        if (Objects.isNull(messageTypeEnum)) {
            throw new MessageException("消息类型非法");
        }
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        String officialAccountUserType = messageParam.get(ClientConstant.USER_ID_TYPE).toString();
        String pushSubject = messageTypeEnum.getPushSubject();
        if (StrUtil.equals(pushSubject, PushSubjectEnum.OFFICIAL_ACCOUNT_TEMPLATE.getCode())) {
            messageParam.put(officialAccountUserType, sendTaskDto.getUsers());
        }
    }
}
