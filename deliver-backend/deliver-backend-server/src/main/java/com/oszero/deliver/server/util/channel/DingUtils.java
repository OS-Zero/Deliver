package com.oszero.deliver.server.util.channel;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.taobao.api.ApiException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DingUtils {

    /**
     * 获取 AccessToken
     *
     * @param dingApp 钉钉App配置
     * @return access_token
     */

    public String getAccessToken(DingApp dingApp) {


        @Data
        class DingDingAccessTokenBody {

            private String errcode;
            private String accessToken;
            private String errmsg;
            private String expiresIn;

        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(dingApp.getAppKey());
        request.setAppsecret(dingApp.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response;
        try {
            response = client.execute(request);
        } catch (ApiException apiException) {
            // XHYTODO:2023/10/23  后续日志记录
            throw new LinkProcessException("获取 tenantAccessTokens失败");
        }

        DingDingAccessTokenBody dingDingAccessTokenBody = JSONUtil.toBean(response.getBody(), DingDingAccessTokenBody.class);
        return dingDingAccessTokenBody.getAccessToken();
    }


    /**
     * 发送钉钉消息
     *
     * @param accessToken 钉钉accessToken
     * @param sendTaskDto 钉钉DTO
     */


    public void sendMessage(String accessToken, SendTaskDto sendTaskDto) {


        @Data
        class DingDingSendInfoBody {

            private Integer errcode;
            private String errmsg;
            private Long taskId;
            private String requestId;
        }

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String body = JSONUtil.toJsonStr(paramMap);
        HttpResponse response = HttpRequest.post("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=" + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(body)
                .execute();

        DingDingSendInfoBody dingDingSendInfoBody = JSONUtil.toBean(response.body(), DingDingSendInfoBody.class);
        if (dingDingSendInfoBody.errcode != 0) {
            throw new LinkProcessException("DingDing消息发送失败!!!");
        }

    }

    /**
     * 校验userId是否存在
     *
     * @param accessToken 钉钉accessToken
     * @param userId      userId
     */

    public void checkId(String accessToken, String userId) {


        @Data
        class DingDingUserInfoBody {
            private Integer errcode;
            private String errmsg;
            private Object result;

        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");
        req.setHttpMethod("POST");
        OapiV2UserGetResponse rsp;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException apiException) {
            // XHYTODO:2023/10/23  后续日志记录
            throw new LinkProcessException("DingDing用户 userId 校验失败！！！");
        }

        DingDingUserInfoBody dingDingUserInfoBody = JSONUtil.toBean(rsp.getBody(), DingDingUserInfoBody.class);
        if (dingDingUserInfoBody.getErrcode() != 0) {
            throw new LinkProcessException("DingDing用户 userId 校验失败！！！");
        }
    }
}
