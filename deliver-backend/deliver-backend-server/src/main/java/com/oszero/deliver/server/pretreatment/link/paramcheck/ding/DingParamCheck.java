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

package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 钉钉参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DingParamCheck implements MessageLink<SendTaskDto> {

    private final Map<String, ParamStrategy> dingParamStrategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String appConfig = sendTaskDto.getAppConfig();
        DingApp dingApp = JSONUtil.toBean(appConfig, DingApp.class);

        // 得到参数 Map
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String pushSubject = paramMap.get("pushSubject").toString();
        String dingUserIdType = paramMap.get("dingUserIdType").toString();
        List<String> users = sendTaskDto.getUsers();

        // 封装通用参数
        if ("robot".equals(pushSubject)) {
            if ("openConversationId".equals(dingUserIdType)) {
                paramMap.put(dingUserIdType, users.get(0));
            } else {
                paramMap.put(dingUserIdType, users);
            }
            paramMap.put("robotCode", dingApp.getRobotCode());
        } else if ("workNotice".equals(pushSubject)) {
            paramMap.put(dingUserIdType, String.join(",", users));
            paramMap.put("agent_id", dingApp.getAgentId());
        }

        try {
            // 参数校验
            String strategyName = ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType();
            ParamStrategy paramStrategy = dingParamStrategyMap.get(strategyName);
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "钉钉消息参数校验失败，" + e.getMessage());
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成钉钉参数校验");
    }
}
