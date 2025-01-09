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
import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.WeChatApp;
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
public class WeChatClient {

    public String getAccessToken(WeChatApp weChatApp) {
        String corpid = weChatApp.getCorpid();
        String corpsecret = weChatApp.getCorpsecret();
        @Data
        class WechatResponse {
            private Integer errcode;
            private String errmsg;
            private String access_token;
            private Integer expires_in;
        }
        WechatResponse weChatResponse;
        try (HttpResponse response = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret)
                .execute()) {
            weChatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(weChatResponse.getErrcode(), 0)) {
                throw new ClientException(weChatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("企业微信获取Token接口调用失败，" + e.getMessage());
        }
        return weChatResponse.getAccess_token();
    }

    public void sendAppMessage(String accessToken, Map<String, Object> paramMap) {
        String body = JSONUtil.toJsonStr(paramMap);
        try (HttpResponse response = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            @Data
            class WechatResponse {
                private Integer errcode;
                private String errmsg;
                private String invaliduser; // 不合法的userid，不区分大小写，统一转为小写
                private String invalidparty; // 不合法的partyid
                private String invalidtag; // 不合法的标签id
                private String unlicenseduser; // 没有基础接口许可(包含已过期)的userid
                private String msgid;
                private String response_code;
            }
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new ClientException(wechatResponse.getErrmsg());
            }
            if (!(wechatResponse.getInvaliduser().isBlank()
                    && wechatResponse.getInvalidparty().isBlank()
                    && wechatResponse.getInvalidtag().isBlank()
                    && wechatResponse.getUnlicenseduser().isBlank())) {
                throw new ClientException("存在异常的企微 userId："
                        + wechatResponse.getInvaliduser()
                        + wechatResponse.getInvalidparty()
                        + wechatResponse.getInvalidtag()
                        + wechatResponse.getUnlicenseduser()
                        + "，请检查 touser 或者 toparty 或者 totag 参数");
            }
        } catch (Exception e) {
            throw new ClientException("企微发送应用消息失败，" + e.getMessage());
        }
    }

    public void sendAppGroupMessage(String accessToken, Map<String, Object> paramMap) {
        String body = JSONUtil.toJsonStr(paramMap);
        try (HttpResponse response = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            @Data
            class WechatResponse {
                private Integer errcode;
                private String errmsg;
            }
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new ClientException(wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("企微发送应用消息到群聊会话失败，" + e.getMessage());
        }
    }

    public void sendAppSchoolMessage(String accessToken, Map<String, Object> paramMap) {
        String body = JSONUtil.toJsonStr(paramMap);
        try (HttpResponse response = HttpRequest.post(" https://qyapi.weixin.qq.com/cgi-bin/externalcontact/message/send?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            @Data
            class WechatResponse {
                private Integer errcode;
                private String errmsg;
                private List<String> invalid_parent_userid;
                private List<String> invalid_student_userid;
                private List<String> invalid_party;
            }
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new ClientException(wechatResponse.getErrmsg());
            }
            if (!(wechatResponse.getInvalid_parent_userid().isEmpty()
                    && wechatResponse.getInvalid_student_userid().isEmpty()
                    && wechatResponse.getInvalid_party().isEmpty())) {
                throw new ClientException("存在异常的企微 userId："
                        + wechatResponse.getInvalid_parent_userid()
                        + wechatResponse.getInvalid_student_userid()
                        + wechatResponse.getInvalid_party()
                        + "，请检查 to_parent_userid 或者 to_student_userid 或者 to_party 参数");
            }
        } catch (Exception e) {
            throw new ClientException("企微发送应用家校消息推送失败，" + e.getMessage());
        }
    }

    public void sendRobotMessage(String accessToken, Map<String, Object> paramMap, List<String> users) {
        String body = JSONUtil.toJsonStr(paramMap);
        try (HttpResponse response = HttpRequest.post(users.get(0))
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute()) {
            @Data
            class WechatResponse {
                private Integer errcode;
                private String errmsg;
            }
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new ClientException(wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new ClientException("企微发送群机器人消息失败，" + e.getMessage());
        }
    }

    public List<String> getUserIdByPhone(String accessToken, List<String> phoneList) {
        return phoneList.stream().map(phone -> {
            try (HttpResponse response = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?access_token=" + accessToken)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .body("{\n" +
                            "   \"mobile\": \"" + phone + "\"\n" +
                            "}")
                    .execute()) {
                @Data
                class WechatResponse {
                    private Integer errcode;
                    private String errmsg;
                    private String userid;
                }
                WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
                if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                    throw new ClientException(wechatResponse.getErrmsg());
                }
                return wechatResponse.getUserid();
            } catch (Exception e) {
                throw new ClientException("企微通过手机号获取 UserId 失败，" + e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    public String uploadWeChatFile(String accessToken, PlatformFileDto platformFileDto) {
        String fileType = platformFileDto.getFileType();
        @Data
        class WechatResponse {
            private Integer errcode;
            private String errmsg;
            private String type;
            private String media_id;
            private String created_at;
        }
        try (HttpResponse response = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=" + fileType)
                .header("Content-Type", "multipart/form-data; boundary=-------------------------acebdf13572468")
                .form("media", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new ClientException(wechatResponse.getErrmsg());
            }
            log.info("上传企微平台文件成功");
            return wechatResponse.getMedia_id();
        } catch (Exception e) {
            throw new ClientException("上传企业微信平台文件失败，" + e.getMessage() + "！！！");
        }
    }
}
