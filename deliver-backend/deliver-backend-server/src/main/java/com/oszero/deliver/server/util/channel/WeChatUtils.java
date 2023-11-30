package com.oszero.deliver.server.util.channel;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 渠道-企业微信工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class WeChatUtils {

    /**
     * 获取企业微信 Token
     *
     * @param weChatApp 企微 APP
     * @return Token
     */
    public String getAccessToken(WeChatApp weChatApp) {

        String corpid = weChatApp.getCorpid();
        String corpsecret = weChatApp.getCorpsecret();
        @Data
        class WeChatResponse {
            private Integer errcode;
            private String errmsg;
            private String access_token;
            private Integer expires_in;
        }

        WeChatResponse weChatResponse;
        try (HttpResponse response = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret)
                .execute()) {

            weChatResponse = JSONUtil.toBean(response.body(), WeChatResponse.class);
            if (!Objects.equals(weChatResponse.getErrcode(), 0)) {
                throw new MessageException("获取企业微信 Token 失败：" + weChatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new MessageException("企业微信获取 Token 接口调用失败！！！");
        }
        log.info("获取企微 Token 成功！");
        return weChatResponse.getAccess_token();
    }

    /**
     * 发送应用消息
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppMessage(String accessToken, SendTaskDto sendTaskDto) {

    }

    /**
     * 发送应用消息到群聊会话
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppGroupMessage(String accessToken, SendTaskDto sendTaskDto) {

    }

    /**
     * 发送应用家校消息推送
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendAppSchoolMessage(String accessToken, SendTaskDto sendTaskDto) {

    }

    /**
     * 发送群机器人消息
     *
     * @param accessToken token
     * @param sendTaskDto dto
     */
    public void sendRobotMessage(String accessToken, SendTaskDto sendTaskDto) {

    }
}
