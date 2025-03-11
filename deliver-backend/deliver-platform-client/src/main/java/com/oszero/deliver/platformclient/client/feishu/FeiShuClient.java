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

package com.oszero.deliver.platformclient.client.feishu;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.FeiShuApp;
import com.oszero.deliver.platformclient.model.dto.PlatformFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道-飞书工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class FeiShuClient {

    public String getTenantAccessToken(FeiShuApp feiShuApp) {
        @Data
        @AllArgsConstructor
        class TenantAccessTokenReqBody {
            private String app_id;
            private String app_secret;
        }
        TenantAccessTokenReqBody tenantAccessTokenReqBody = new TenantAccessTokenReqBody(feiShuApp.getAppId(), feiShuApp.getAppSecret());

        String body;
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
                .header("Content-Type", "application/json; charset=utf-8")
                .body(JSONUtil.toJsonStr(tenantAccessTokenReqBody))
                .execute()) {
            @Data
            class TenantAccessTokenRespBody {
                private Integer code;
                private String msg;
                private String tenant_access_token;
                private Integer expire;
            }
            body = response.body();
            TenantAccessTokenRespBody tenantAccessTokenRespBody = JSONUtil.toBean(body, TenantAccessTokenRespBody.class);
            if (!Objects.equals(tenantAccessTokenRespBody.getCode(), 0)) {
                throw new ClientException(tenantAccessTokenRespBody.getMsg());
            }
            return "Bearer " + tenantAccessTokenRespBody.getTenant_access_token();
        } catch (Exception e) {
            throw new ClientException("获取飞书 tenantAccessToken 失败，" + e.getMessage());
        }
    }

    public void sendMessage(String tenantAccessToken, String userId, String contentJson, String msgType, String feiShuUserIdType) {
        @Data
        class SendMessageResponse {
            private Integer code;
            private String msg;
            private Object data;
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("receive_id", userId);
        paramMap.put("msg_type", msgType);
        paramMap.put("content", contentJson);
        paramMap.put("uuid", UUID.randomUUID().toString());
        String jsonStr = JSONUtil.toJsonStr(paramMap);
        SendMessageResponse sendMessageResponse;
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/messages?receive_id_type=" + feiShuUserIdType)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(jsonStr)
                .execute()) {
            sendMessageResponse = JSONUtil.toBean(response.body(), SendMessageResponse.class);
            if (!sendMessageResponse.getCode().equals(0)) {
                throw new ClientException(sendMessageResponse.getMsg());
            }
        } catch (Exception e) {
            throw new ClientException(e.getMessage());
        }
    }

    public void sendMessageBatch(String tenantAccessToken, Map<String, Object> paramMap) {
        @Data
        class SendMessageResponse {
            private Integer code;
            private String msg;
            private Object data;
        }
        String body = JSONUtil.toJsonStr(paramMap);
        SendMessageResponse sendMessageResponse;
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/message/v4/batch_send/")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute()) {
            sendMessageResponse = JSONUtil.toBean(response.body(), SendMessageResponse.class);
            if (!sendMessageResponse.getCode().equals(0)) {
                throw new ClientException(sendMessageResponse.getMsg());
            }
        } catch (Exception e) {
            throw new ClientException("飞书消息发送失败，" + e.getMessage());
        }
    }

    public List<String> getUserIdsByPhones(String tenantAccessToken, List<String> phones) {
        @Data
        class PhoneRequestBody {
            private List<String> mobiles;
        }
        PhoneRequestBody phoneRequestBody = new PhoneRequestBody();
        phoneRequestBody.setMobiles(phones);
        String body = JSONUtil.toJsonStr(phoneRequestBody);
        String respBody;
        try (HttpResponse execute = HttpRequest.post("https://open.feishu.cn/open-apis/contact/v3/users/batch_get_id" + "?user_id_type=user_id")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute()) {
            @Data
            class UserIdAndPhone {
                private String user_id;
                private String mobile;
            }
            @Data
            class DataResp {
                List<UserIdAndPhone> user_list;
            }
            @Data
            class FeiShuUserIdRespBody {
                private Integer code;
                private String msg;
                private DataResp data;
            }
            respBody = execute.body();
            FeiShuUserIdRespBody feiShuUserIdRespBody = JSONUtil.toBean(respBody, FeiShuUserIdRespBody.class);
            if (!Objects.equals(feiShuUserIdRespBody.getCode(), 0)) {
                throw new ClientException(feiShuUserIdRespBody.getMsg());
            }
            return feiShuUserIdRespBody.getData().getUser_list().stream().map(UserIdAndPhone::getUser_id).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ClientException("飞书手机号转换用户ID失败，" + e.getMessage());
        }
    }

    public String uploadFeiShuFile(String tenantAccessToken, PlatformFileDto platformFileDto) {
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/files")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("file_type", platformFileDto.getFileType())
                .form("file_name", platformFileDto.getFileName())
                .form("file", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
            @Data
            class FeiShuUploadFileResponse {
                private Integer code;
                private String msg;
                private Data data;

                @lombok.Data
                static class Data {
                    private String file_key;
                }
            }
            FeiShuUploadFileResponse feiShuUploadFileResponse = JSONUtil.toBean(response.body(), FeiShuUploadFileResponse.class);
            if (!feiShuUploadFileResponse.getCode().equals(0) || StrUtil.isBlank(feiShuUploadFileResponse.getData().getFile_key())) {
                throw new ClientException(feiShuUploadFileResponse.getMsg());
            }
            return feiShuUploadFileResponse.getData().getFile_key();
        } catch (Exception e) {
            throw new ClientException("上传飞书文件失败，" + e.getMessage());
        }
    }

    public String uploadFeiShuImageFile(String tenantAccessToken, PlatformFileDto platformFileDto) {
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/images")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("image_type", "message")
                .form("image", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
            @Data
            class FeiShuUploadImageResponse {
                private Integer code;
                private String msg;
                private Data data;
                @lombok.Data
                static class Data {
                    private String image_key;
                }
            }
            FeiShuUploadImageResponse feiShuUploadImageResponse = JSONUtil.toBean(response.body(), FeiShuUploadImageResponse.class);
            if (!feiShuUploadImageResponse.getCode().equals(0) || StrUtil.isBlank(feiShuUploadImageResponse.getData().getImage_key())) {
                throw new ClientException(feiShuUploadImageResponse.getMsg());
            }
            return feiShuUploadImageResponse.getData().getImage_key();
        } catch (Exception e) {
            throw new ClientException("上传飞书图片失败，" + e.getMessage());
        }
    }
}
