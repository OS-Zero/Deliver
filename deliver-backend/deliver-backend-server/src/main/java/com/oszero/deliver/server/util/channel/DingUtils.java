package com.oszero.deliver.server.util.channel;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.taobao.api.ApiException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 渠道-钉钉工具类
 *
 * @author oszero
 * @version 1.0.0
 */
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
        class DingAccessTokenBody {

            private Integer errcode;
            private String accessToken;
            private String errmsg;
            private Integer expiresIn;

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
            // TODO:2023/10/23  后续日志记录
            throw new LinkProcessException("获取 tenantAccessTokens 失败");
        }

        DingAccessTokenBody dingAccessTokenBody = JSONUtil.toBean(response.getBody(), DingAccessTokenBody.class);
        return dingAccessTokenBody.getAccessToken();
    }

    /**
     * 发送钉钉消息
     *
     * @param accessToken 钉钉accessToken
     * @param sendTaskDto 钉钉DTO
     */
    public void sendMessage(String accessToken, SendTaskDto sendTaskDto) {

        @Data
        class DingSendInfoBody {

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

        DingSendInfoBody dingSendInfoBody = JSONUtil.toBean(response.body(), DingSendInfoBody.class);
        if (dingSendInfoBody.errcode != 0) {
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
        class DingUserInfoBody {
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
            throw new LinkProcessException("钉钉用户 userId 校验失败！！！");
        }

        DingUserInfoBody dingUserInfoBody = JSONUtil.toBean(rsp.getBody(), DingUserInfoBody.class);
        if (dingUserInfoBody.getErrcode() != 0) {
            throw new LinkProcessException("钉钉用户 userId 校验失败！！！");
        }
    }


    /**
     * 根据电话号码获取userId
     *
     * @param accessToken    钉钉accessToken
     * @param phone          电话号码
     * @return
     */

    public String getUserIdByPhone(String accessToken, String phone) {

        @Data
        class Result {
            private String userid;
            private String[] exclusiveAccountUseridList;
        }
        @Data
        class DingResponseBody {
            private Integer errcode;
            private String requestId;
            private Result result;
            private String errmsg;
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(phone);
        req.setSupportExclusiveAccountSearch(true);
        OapiV2UserGetbymobileResponse rsp;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new LinkProcessException("转换 userId 失败 ！！！");
        }
        DingResponseBody dingResponseBody = JSONUtil.toBean(rsp.getBody(), DingResponseBody.class);
        if (dingResponseBody.getErrcode() != 0) {
            throw new LinkProcessException("转换 userId 失败 ！！！");
        }


        return dingResponseBody.getResult().getUserid();

    }
}
