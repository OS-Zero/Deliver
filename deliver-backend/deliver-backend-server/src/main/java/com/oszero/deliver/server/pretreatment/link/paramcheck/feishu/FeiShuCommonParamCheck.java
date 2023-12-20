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

package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 飞书通用参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class FeiShuCommonParamCheck implements MessageLink<SendTaskDto> {
    private static final Set<String> FEI_SHU_USER_ID_TYPE_SET = new HashSet<>(
            Arrays.asList("user_id", "email", "chat_id", "department_id"));
    /**
     * 可以通过 department_id 发送的消息类型
     */
    private static final Set<String> DEPARTMENT_MESSAGE_TYPE =
            new HashSet<>(Arrays.asList(
                    MessageTypeEnum.TEXT.getCode(),
                    MessageTypeEnum.FEI_SHU_POST.getCode(),
                    MessageTypeEnum.FEI_SHU_IMAGE.getCode(),
                    MessageTypeEnum.FEI_SHU_INTERACTIVE.getCode(),
                    MessageTypeEnum.FEI_SHU_SHARE_CHAT.getCode()));

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String feiShuUserIdType = paramMap.getOrDefault("feiShuUserIdType", "").toString();

        if (StrUtil.isBlank(feiShuUserIdType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 参数为空");
        }
        if (!FEI_SHU_USER_ID_TYPE_SET.contains(feiShuUserIdType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 参数非法，必须为 user_id 或者 email 或者 chat_id 或者 department_id");
        }
        if ("department_id".equals(feiShuUserIdType) && !DEPARTMENT_MESSAGE_TYPE.contains(messageType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 为 department_id 时，不支持此消息类型");
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成飞书通用参数校验");
    }
}
