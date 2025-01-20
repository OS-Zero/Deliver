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

package com.oszero.deliver.business.server.pretreatment.link.convert;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.util.AppConfigUtils;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.pretreatment.common.LinkContext;
import com.oszero.deliver.business.server.pretreatment.common.MessageLink;
import com.oszero.deliver.business.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.platformclient.client.ding.DingClient;
import com.oszero.deliver.platformclient.client.feishu.FeiShuClient;
import com.oszero.deliver.platformclient.client.wechat.WeChatClient;
import com.oszero.deliver.platformclient.model.app.DingApp;
import com.oszero.deliver.platformclient.model.app.FeiShuApp;
import com.oszero.deliver.platformclient.model.app.WeChatApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class Phone2UserIdConvert implements MessageLink<SendTaskDto> {

    private final Map<String, Phone2UserId> strategyMap;
    private final AppConfigUtils appConfigUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        String appConfigJson = appConfigUtils.decryptAppConfig(sendTaskDto.getAppConfig());
        Phone2UserId phone2UserId = strategyMap.get(PretreatmentCodeConstant.CONVERT_PRE + context.getCode());
        sendTaskDto.setUsers(phone2UserId.convert(appConfigJson, users));
    }

    public interface Phone2UserId {
        List<String> convert(String appConfigJson, List<String> phones);
    }

    @RequiredArgsConstructor
    public static class DingStrategy implements Phone2UserId {
        private final DingClient dingClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            String accessToken = dingClient.getAccessToken(JSONUtil.toBean(appConfigJson, DingApp.class));
            return phones.stream().map(phone -> dingClient.getUserIdByPhone(accessToken, phone)).collect(Collectors.toList());
        }
    }

    @RequiredArgsConstructor
    public static class WeChatStrategy implements Phone2UserId {
        private final WeChatClient weChatClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            String accessToken = weChatClient.getAccessToken(JSONUtil.toBean(appConfigJson, WeChatApp.class));
            return weChatClient.getUserIdByPhone(accessToken, phones);
        }
    }

    @RequiredArgsConstructor
    public static class FeiShuStrategy implements Phone2UserId {
        private final FeiShuClient feiShuClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            String tenantAccessToken = feiShuClient.getTenantAccessToken(JSONUtil.toBean(appConfigJson, FeiShuApp.class));
            return feiShuClient.getUserIdsByPhones(tenantAccessToken, phones);
        }
    }
}
