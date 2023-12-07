package com.oszero.deliver.server.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
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
                throw new MessageException(sendTaskDto, "获取企业微信 Token 失败，" + weChatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企业微信获取 Token 接口调用失败");
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
                private String invaliduser;
                private String invalidparty;
                private String invalidtag;
                private String unlicenseduser;
                private String msgid;
                private String response_code;
            }

            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new MessageException(sendTaskDto, "企微发送应用消息失败，" + wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用消息失败，服务调用异常");
        }
        log.info("企微发送应用消息成功");
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
                throw new MessageException(sendTaskDto, "企微发送应用消息到群聊会话失败，" + wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用消息到群聊会话失败，服务调用异常");
        }
        log.info("企微发送应用消息到群聊会话成功");
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
                throw new MessageException(sendTaskDto, "企微发送应用家校消息推送失败，" + wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送应用家校消息推送失败，服务调用异常");
        }
        log.info("企微发送应用家校消息推送成功");
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
                throw new MessageException(sendTaskDto, "企微发送群机器人消息失败，" + wechatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "企微发送群机器人消息失败，服务调用异常");
        }
        log.info("企微发送群机器人消息成功");
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
                    throw new MessageException(sendTaskDto, "企微通过手机号获取 UserId 失败，" + wechatResponse.getErrmsg());
                }
                return wechatResponse.getUserid();
            } catch (Exception e) {
                throw new MessageException(sendTaskDto, "企微通过手机号获取 UserId 失败，服务异常");
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
                    throw new MessageException(sendTaskDto, "企微校验用户 ID 失败，" + wechatResponse.getErrmsg());
                }
            } catch (Exception e) {
                throw new MessageException(sendTaskDto, "企微校验用户 ID 失败，服务异常");
            }
        });
        log.info("企微校验用户 ID 成功");
    }
}
