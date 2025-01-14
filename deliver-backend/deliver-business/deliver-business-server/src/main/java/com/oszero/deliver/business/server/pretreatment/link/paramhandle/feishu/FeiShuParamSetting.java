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

package com.oszero.deliver.business.server.pretreatment.link.paramhandle.feishu;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.platformclient.common.ClientConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeiShuParamSetting implements MessageLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(sendTaskDto.getMessageType());
        if (messageTypeEnum == null) {
            throw new RuntimeException("消息类型非法");
        }
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        String feiShuUserIdType = messageParam.get(ClientConstant.USER_ID_TYPE).toString();
        if (StrUtil.equals(feiShuUserIdType, ClientConstant.FEI_SHU_DEPT_ID)) {
            messageParam.put(ClientConstant.FEI_SHU_DEPT_IDS, sendTaskDto.getUsers());
        } else if (StrUtil.equals(feiShuUserIdType, ClientConstant.FEI_SHU_USER_ID)) {
            messageParam.put(ClientConstant.FEI_SHU_USER_IDS, sendTaskDto.getUsers());
        }
        messageParam.put(ClientConstant.FEI_SHU_MSG_TYPE, messageTypeEnum.getMsgType());
    }
}
