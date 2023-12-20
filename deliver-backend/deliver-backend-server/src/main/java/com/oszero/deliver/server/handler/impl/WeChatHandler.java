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

package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.cache.manager.ServerCacheManager;
import com.oszero.deliver.server.client.wechat.WeChatClient;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * 企业微信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class WeChatHandler extends BaseHandler {

    private final WeChatClient weChatClient;
    private final ServerCacheManager serverCacheManager;

    public WeChatHandler(WeChatClient weChatClient, ServerCacheManager serverCacheManager, MessageRecordService messageRecordService) {
        this.weChatClient = weChatClient;
        this.serverCacheManager = serverCacheManager;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfig = sendTaskDto.getAppConfig();
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);

        String accessToken = serverCacheManager.getWeChatToken(weChatApp, sendTaskDto);

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.get("pushSubject").toString();
        String wechatUserIdType = paramMap.get("wechatUserIdType").toString();

        if ("app".equals(pushSubject)) {
            if (new HashSet<>(Arrays.asList("touser", "toparty", "totag")).contains(wechatUserIdType)) {
                weChatClient.sendAppMessage(accessToken, sendTaskDto);
            } else if (new HashSet<>(Arrays.asList("to_parent_userid", "to_student_userid", "to_party", "toall")).contains(wechatUserIdType)) {
                weChatClient.sendAppSchoolMessage(accessToken, sendTaskDto);
            } else {
                weChatClient.sendAppGroupMessage(accessToken, sendTaskDto);
            }
        } else if ("robot".equals(pushSubject)) {
            weChatClient.sendRobotMessage(accessToken, sendTaskDto);
        }
        // 删除掉标识参数
        paramMap.remove("pushSubject");
        paramMap.remove("wechatUserIdType");
    }
}
