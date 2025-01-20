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
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.enums.PushSubjectEnum;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.platformclient.common.ClientConstant;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class DingParamCheck implements MessageLink<SendTaskDto> {
    private static final Set<String> DING_WORK_NOTICE_USER_ID_TYPE_SET = new HashSet<>(
            Arrays.asList(ClientConstant.DING_USER_ID_LIST, ClientConstant.DING_DEPT_ID_LIST, ClientConstant.DING_TO_ALL));
    private static final Set<String> DING_ROBOT_USER_ID_TYPE_SET = new HashSet<>(
            Arrays.asList(ClientConstant.DING_USER_IDS, ClientConstant.DING_OPEN_CONVERSATION_ID));

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getInstanceByCode(messageType);
        if (Objects.isNull(messageTypeEnum)) {
            throw new RuntimeException("消息类型非法");
        }
        String pushSubject = messageTypeEnum.getPushSubject();
        Map<String, Object> paramMap = sendTaskDto.getMessageParam();
        String dingUserIdType = paramMap.getOrDefault(ClientConstant.USER_ID_TYPE, "").toString();
        if (StrUtil.equals(PushSubjectEnum.DING_WORK_NOTICE.getCode(), pushSubject)) {
            if (!DING_WORK_NOTICE_USER_ID_TYPE_SET.contains(dingUserIdType)) {
                throw new RuntimeException("钉钉工作通知消息userIdType非法，应为userid_list 或者 dept_id_list 或者 to_all_user");
            }
        } else if (StrUtil.equals(PushSubjectEnum.DING_ROBOT.getCode(), pushSubject)) {
            if (!DING_ROBOT_USER_ID_TYPE_SET.contains(dingUserIdType)) {
                throw new RuntimeException("钉钉机器人消息userIdType非法，应为userIds 或者 openConversationId");
            }
        }
    }
}