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

package com.oszero.deliver.server.client.wechat;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道-企业微信工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class WeChatClient {

    /**
     * 获取企业微信 Token
     *
     * @param weChatApp 企微 APP
     * @return Token
     */
    public String getAccessToken(WeChatApp weChatApp, SendTaskDto sendTaskDto) {

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
                throw new MessageException(weChatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企业微信获取 Token 接口调用失败，" + e.getMessage());
        }
        log.info("获取企微 Token 成功");
        return weChatResponse.getAccess_token();
    }

    /**
     * 发送应用消息
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppMessage(String accessToken, SendTaskDto sendTaskDto) {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
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
                throw new MessageException(wechatResponse.getErrmsg());
            }
            if (!(wechatResponse.getInvaliduser().isBlank()
                    && wechatResponse.getInvalidparty().isBlank()
                    && wechatResponse.getInvalidtag().isBlank()
                    && wechatResponse.getUnlicenseduser().isBlank())) {
                throw new MessageException("存在异常的企微 userId："
                        + wechatResponse.getInvaliduser()
                        + wechatResponse.getInvalidparty()
                        + wechatResponse.getInvalidtag()
                        + wechatResponse.getUnlicenseduser()
                        + "，请检查 touser 或者 toparty 或者 totag 参数");
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用消息失败，" + e.getMessage());
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "企微发送应用消息成功");
    }

    /**
     * 发送应用消息到群聊会话
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppGroupMessage(String accessToken, SendTaskDto sendTaskDto) {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
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
                throw new MessageException(wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用消息到群聊会话失败，" + e.getMessage());
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "企微发送应用消息到群聊会话成功");
    }

    /**
     * 发送应用家校消息推送
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppSchoolMessage(String accessToken, SendTaskDto sendTaskDto) {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
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
                throw new MessageException(wechatResponse.getErrmsg());
            }
            if (!(wechatResponse.getInvalid_parent_userid().isEmpty()
                    && wechatResponse.getInvalid_student_userid().isEmpty()
                    && wechatResponse.getInvalid_party().isEmpty())) {
                throw new MessageException("存在异常的企微 userId："
                        + wechatResponse.getInvalid_parent_userid()
                        + wechatResponse.getInvalid_student_userid()
                        + wechatResponse.getInvalid_party()
                        + "，请检查 to_parent_userid 或者 to_student_userid 或者 to_party 参数");
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用家校消息推送失败，" + e.getMessage());
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "企微发送应用家校消息推送成功");
    }

    /**
     * 发送群机器人消息
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendRobotMessage(String accessToken, SendTaskDto sendTaskDto) {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        List<String> users = sendTaskDto.getUsers();
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
                throw new MessageException(wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送群机器人消息失败，" + e.getMessage());
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "企微发送群机器人消息成功");
    }

    /**
     * 通过手机号获取 UserId
     *
     * @param accessToken Token
     * @param phoneList   phoneList
     * @return IDs
     */
    public List<String> getUserIdByPhone(String accessToken, List<String> phoneList, SendTaskDto sendTaskDto) {

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
                    throw new MessageException(wechatResponse.getErrmsg());
                }
                return wechatResponse.getUserid();
            } catch (Exception e) {
                throw new MessageException(sendTaskDto, "企微通过手机号获取 UserId 失败，" + e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    /**
     * 校验用户 ID
     *
     * @param accessToken Token
     * @param userIdList  ID
     */
    public void checkUserId(String accessToken, List<String> userIdList, SendTaskDto sendTaskDto) {

        userIdList.forEach(userId -> {
            try (HttpResponse response = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken + "&userid=" + userId)
                    .execute()) {
                @Data
                class WechatResponse {
                    private Integer errcode;
                    private String errmsg;
                }
                WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
                if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                    throw new MessageException(wechatResponse.getErrmsg());
                }
            } catch (Exception e) {
                throw new MessageException(sendTaskDto, "企微校验用户 ID 失败，" + e.getMessage());
            }
        });
    }
}
