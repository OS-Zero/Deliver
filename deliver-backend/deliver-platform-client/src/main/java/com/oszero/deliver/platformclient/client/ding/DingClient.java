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

package com.oszero.deliver.platformclient.client.ding;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.DingApp;
import com.oszero.deliver.platformclient.model.dto.PlatformFileDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
public class DingClient {

    public String getAccessToken(DingApp dingApp) {
        String appKey = dingApp.getAppKey();
        String appSecret = dingApp.getAppSecret();
        @Data
        class DingAccessTokenBody {
            private Integer errcode;
            private String accessToken;
            private String errmsg;
            private Integer expiresIn;
        }
        DingAccessTokenBody dingAccessTokenBody;
        try (HttpResponse response = HttpRequest.get("https://oapi.dingtalk.com/gettoken?appkey=" + appKey + "&appsecret=" + appSecret)
                .execute()) {
            dingAccessTokenBody = JSONUtil.toBean(response.body(), DingAccessTokenBody.class);
            if (!Objects.equals(dingAccessTokenBody.getErrcode(), 0)) {
                throw new ClientException(dingAccessTokenBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("钉钉获取Token接口调用失败，" + e.getMessage());
        }
        return dingAccessTokenBody.getAccessToken();
    }

    public void sendWorkNoticeMessage(String accessToken, Map<String, Object> paramMap) {
        @Data
        class DingSendInfoResponseBody {
            private Integer errcode;
            private String errmsg;
            private Long taskId;
            private String requestId;
        }
        String body = JSONUtil.toJsonStr(paramMap);
        DingSendInfoResponseBody dingSendInfoResponseBody;
        try (HttpResponse response = HttpRequest.post("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            dingSendInfoResponseBody = JSONUtil.toBean(response.body(), DingSendInfoResponseBody.class);
            if (!Objects.equals(dingSendInfoResponseBody.getErrcode(), 0)) {
                throw new ClientException(dingSendInfoResponseBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("钉钉发送工作通知接口调用失败，" + e.getMessage());
        }
    }

    public void sendRobotMessage(String accessToken, Map<String, Object> paramMap) {
        @Data
        class DingSendInfoResponseBody {
            private String processQueryKey;
            private List<String> invalidStaffIdList;
            private List<String> flowControlledStaffIdList;
        }
        // 选择请求地址
        String url = "https://api.dingtalk.com" + (paramMap.containsKey("userIds") ? "/v1.0/robot/oToMessages/batchSend" : "/v1.0/robot/groupMessages/send");
        String body = JSONUtil.toJsonStr(paramMap);
        DingSendInfoResponseBody dingSendInfoResponseBody;
        try (HttpResponse response = HttpRequest.post(url)
                .header("x-acs-dingtalk-access-token", accessToken)
                .header("Content-Type", "application/json")
                .body(body).execute()) {
            dingSendInfoResponseBody = JSONUtil.toBean(response.body(), DingSendInfoResponseBody.class);
            if (!(dingSendInfoResponseBody.invalidStaffIdList.isEmpty() && dingSendInfoResponseBody.flowControlledStaffIdList.isEmpty())) {
                throw new ClientException("");
            }
        } catch (Exception e) {
            throw new ClientException("钉钉发送钉钉机器人消息接口调用失败，" + e.getMessage());
        }
    }

    public String getUserIdByPhone(String accessToken, String phone) {
        @Data
        class RequestBody {
            private String mobile;
            private boolean support_exclusive_account_search;
        }
        @Data
        class Result {
            private String userid;
            private String[] exclusive_account_userid_list;
        }
        @Data
        class DingResponseBody {
            private Integer errcode;
            private String requestId;
            private Result result;
            private String errmsg;
        }
        RequestBody requestBody = new RequestBody();
        requestBody.setMobile(phone);
        requestBody.setSupport_exclusive_account_search(true);
        DingResponseBody dingResponseBody;
        try (HttpResponse response = HttpRequest.post("https://oapi.dingtalk.com/topapi/v2/user/getbymobile?access_token=" + accessToken)
                .body(JSONUtil.toJsonStr(requestBody))
                .execute()) {
            dingResponseBody = JSONUtil.toBean(response.body(), DingResponseBody.class);
            if (!Objects.equals(dingResponseBody.getErrcode(), 0)) {
                throw new ClientException(dingResponseBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("钉钉根据电话号码获取userId接口调用失败，" + e.getMessage());
        }
        return dingResponseBody.getResult().getUserid();
    }

    public String uploadDingFile(String accessToken, PlatformFileDto platformFileDto) {
        @Data
        class DingBody {
            private Integer errcode;
            private String errmsg;
            private String mediaId;
            private String createdAt;
            private String type;
        }
        DingBody dingBody;
        try (HttpResponse response = HttpRequest.post("https://oapi.dingtalk.com/media/upload?access_token=" + accessToken)
                .header("Content-Type", "multipart/form-data")
                .form("type", platformFileDto.getFileType())
                .form("media", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
            dingBody = JSONUtil.toBean(response.body(), DingBody.class);
            if (!Objects.equals(dingBody.getErrcode(), 0)) {
                throw new ClientException(dingBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("上传钉钉文件失败，" + e.getMessage());
        }
        return dingBody.getMediaId();
    }
}
