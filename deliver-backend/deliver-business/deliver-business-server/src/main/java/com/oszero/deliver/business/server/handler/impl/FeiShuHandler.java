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
import com.oszero.deliver.business.common.enums.MessageTypeEnum;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.platformclient.client.feishu.FeiShuClient;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.model.app.FeiShuApp;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class FeiShuHandler extends BaseHandler {

    private final FeiShuClient feiShuClient;
    private final AppConfigUtils appConfigUtils;

    private static final Set<String> BATCH_MESSAGE_TYPE =
            new HashSet<>(Arrays.asList(
                    MessageTypeEnum.FEI_SHU_TEXT.getCode(),
                    MessageTypeEnum.FEI_SHU_IMAGE.getCode(),
                    MessageTypeEnum.FEI_SHU_POST.getCode(),
                    MessageTypeEnum.FEI_SHU_SHARE_CHAT.getCode(),
                    MessageTypeEnum.FEI_SHU_INTERACTIVE.getCode())
            );

    public FeiShuHandler(FeiShuClient feiShuClient, AppConfigUtils appConfigUtils) {
        this.feiShuClient = feiShuClient;
        this.appConfigUtils = appConfigUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) {
        String appConfig = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfig, FeiShuApp.class);
        String tenantAccessToken = feiShuClient.getTenantAccessToken(feiShuApp);
        Map<String, Object> messageParam = sendTaskDto.getMessageParam();
        String feiShuUserIdType = messageParam.get(ClientConstant.USER_ID_TYPE).toString();
        String msgType = messageParam.get(ClientConstant.FEI_SHU_MSG_TYPE).toString();
        messageParam.remove(ClientConstant.USER_ID_TYPE);
        List<String> users = sendTaskDto.getUsers();
        // 支持发送多种飞书的 usersId，包括 [用户(user_id),用户(email),群组(chat_id),部门(department_id)]
        if (ClientConstant.FEI_SHU_USER_ID.equals(feiShuUserIdType)) {
            // 如果满足批量发送的用户类型则批量发送
            if (BATCH_MESSAGE_TYPE.contains(msgType)) {
                feiShuClient.sendMessageBatch(tenantAccessToken, messageParam);
            } else { // 否则单个发送
                sendMessageToBatch(tenantAccessToken, users, messageParam, feiShuUserIdType);
            }
        } else if (ClientConstant.FEI_SHU_EMAIL.equals(feiShuUserIdType)) {
            // 邮箱类型只支持单个发送
            sendMessageToBatch(tenantAccessToken, users, messageParam, feiShuUserIdType);
        } else if (ClientConstant.FEI_SHU_CHAT_ID.equals((feiShuUserIdType))) {
            // 群聊类型只支持单个发送
            sendMessageToBatch(tenantAccessToken, users, messageParam, feiShuUserIdType);
        } else if (ClientConstant.FEI_SHU_DEPT_ID.equals((feiShuUserIdType))) {
            // 部门只能批量
            feiShuClient.sendMessageBatch(tenantAccessToken, messageParam);
        }
    }

    public void sendMessageToBatch(String tenantAccessToken, List<String> users, Map<String, Object> paramMap, String feiShuUserIdType) {
        try {
            Object content = paramMap.get("content");
            String msgType = paramMap.get("msg_type").toString();
            String contentJson = JSONUtil.toJsonStr(content);
            users.forEach(userId -> feiShuClient.sendMessage(tenantAccessToken, userId, contentJson, msgType, feiShuUserIdType));
        } catch (Exception e) {
            throw new MessageException("飞书消息发送失败，" + e.getMessage());
        }
    }
}
