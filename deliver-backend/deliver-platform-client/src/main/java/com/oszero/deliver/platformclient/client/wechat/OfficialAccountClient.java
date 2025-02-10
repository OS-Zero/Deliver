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

package com.oszero.deliver.platformclient.client.wechat;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.OfficialAccountApp;
import com.oszero.deliver.platformclient.model.dto.PlatformFileDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class OfficialAccountClient {

    public String getAccessToken(OfficialAccountApp officialAccountApp) {
        String appid = officialAccountApp.getAppid();
        String secret = officialAccountApp.getSecret();
        @Data
        class OfficialAccountResponse {
            private Integer errcode;
            private String errmsg;
            private String access_token;
            private Integer expires_in;
        }
        OfficialAccountResponse officialAccountResponse;
        try (HttpResponse response = HttpRequest.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret)
                .execute()) {
            officialAccountResponse = JSONUtil.toBean(response.body(), OfficialAccountResponse.class);
            if (Objects.nonNull(officialAccountResponse.getErrcode())) {
                throw new ClientException(officialAccountResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("微信公众号获取Token接口调用失败，" + e.getMessage());
        }
        return officialAccountResponse.getAccess_token();
    }

    public void sendMessage(String accessToken, Map<String, Object> paramMap, List<String> users) {
        paramMap.remove(ClientConstant.OFFICIAL_ACCOUNT_USER_ID);
        users.forEach(user -> {
            paramMap.put(ClientConstant.OFFICIAL_ACCOUNT_USER_ID, user);
            sendMessage(accessToken, paramMap);
        });
    }

    public void sendMessage(String accessToken, Map<String, Object> paramMap) {
        String body = JSONUtil.toJsonStr(paramMap);
        try (HttpResponse response = HttpRequest.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            @Data
            class OfficialAccountResponse {
                private Integer errcode;
                private String errmsg;
                private String msgid;
            }
            OfficialAccountResponse officialAccountResponse = JSONUtil.toBean(response.body(), OfficialAccountResponse.class);
            if (!Objects.equals(officialAccountResponse.getErrcode(), 0)) {
                throw new ClientException(officialAccountResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("微信公众号发送模板消息失败，" + e.getMessage());
        }
    }
}
