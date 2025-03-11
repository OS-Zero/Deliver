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

package com.oszero.deliver.business.server.limit.ding;

import com.oszero.deliver.business.server.annotation.ClientRateLimit;
import com.oszero.deliver.business.server.limit.ClientLimitConstant;
import com.oszero.deliver.platformclient.client.ding.DingClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author oszero
 * @version 1.0.2
 */
@Component
public class DingLimitClient extends DingClient {
    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.DING_SEND_WORK_NOTICE)
    public void sendWorkNoticeMessage(String accessToken, Map<String, Object> paramMap) {
        super.sendWorkNoticeMessage(accessToken, paramMap);
    }

    @Override
    @ClientRateLimit(resourceName = ClientLimitConstant.DING_SEND_ROBOT)
    public void sendRobotMessage(String accessToken, Map<String, Object> paramMap) {
        super.sendRobotMessage(accessToken, paramMap);
    }
}
