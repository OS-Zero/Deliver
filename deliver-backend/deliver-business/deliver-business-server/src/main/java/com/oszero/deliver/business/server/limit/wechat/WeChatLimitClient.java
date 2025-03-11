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

package com.oszero.deliver.business.server.limit.wechat;

import com.oszero.deliver.business.server.annotation.ClientRateLimit;
import com.oszero.deliver.business.server.limit.ClientLimitConstant;
import com.oszero.deliver.platformclient.client.wechat.WeChatClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author oszero
 * @version 1.0.2
 */
@Component
public class WeChatLimitClient extends WeChatClient {
    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.WE_CHAT_SEND_APP_MESSAGE)
    public void sendAppMessage(String accessToken, Map<String, Object> paramMap) {
        super.sendAppMessage(accessToken, paramMap);
    }

    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.WE_CHAT_SEND_APP_GROUP_MESSAGE)
    public void sendAppGroupMessage(String accessToken, Map<String, Object> paramMap) {
        super.sendAppGroupMessage(accessToken, paramMap);
    }

    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.WE_CHAT_SEND_APP_SCHOOL_MESSAGE)
    public void sendAppSchoolMessage(String accessToken, Map<String, Object> paramMap) {
        super.sendAppSchoolMessage(accessToken, paramMap);
    }

    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.WE_CHAT_SEND_ROBOT_MESSAGE)
    public void sendRobotMessage(Map<String, Object> paramMap, List<String> users) {
        super.sendRobotMessage(paramMap, users);
    }
}
