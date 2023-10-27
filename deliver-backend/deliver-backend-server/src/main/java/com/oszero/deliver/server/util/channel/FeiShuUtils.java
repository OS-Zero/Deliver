package com.oszero.deliver.server.util.channel;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.lark.oapi.Client;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.service.contact.v3.model.GetUserReq;
import com.lark.oapi.service.contact.v3.model.GetUserResp;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 渠道-飞书工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class FeiShuUtils {

    /**
     * 获取 tenantAccessToken
     *
     * @param feiShuApp 飞书 APP 配置
     * @return 返回 tenantAccessToken
     */
    public String getTenantAccessToken(FeiShuApp feiShuApp) {
        @Data
        @AllArgsConstructor
        class TenantAccessTokenReqBody {
            private String app_id;
            private String app_secret;
        }
        TenantAccessTokenReqBody tenantAccessTokenReqBody =
                new TenantAccessTokenReqBody(feiShuApp.getAppId(), feiShuApp.getAppSecret());
        HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
                .header("Content-Type", "application/json; charset=utf-8")
                .body(JSONUtil.toJsonStr(tenantAccessTokenReqBody))
                .execute();
        @Data
        class TenantAccessTokenRespBody {
            private Integer code;
            private String msg;
            private String tenant_access_token;
            private Integer expire;
        }
        String body = response.body();
        TenantAccessTokenRespBody tenantAccessTokenRespBody = JSONUtil.toBean(body, TenantAccessTokenRespBody.class);
        return "Bearer " + tenantAccessTokenRespBody.getTenant_access_token();
    }

    /**
     * 发送消息
     *
     * @param tenantAccessToken 飞书 token
     * @param sendTaskDto       消息 dto
     */
    public void sendMessage(String tenantAccessToken, SendTaskDto sendTaskDto) {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String body = JSONUtil.toJsonStr(paramMap);
        HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/message/v4/batch_send/")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute();
    }

    /**
     * 检查 userId 是否正确
     *
     * @param tenantAccessToken token
     * @param userId            id
     */
    public void checkUserId(String tenantAccessToken, String userId) {
        HttpResponse execute = HttpRequest.get("https://open.feishu.cn/open-apis/contact/v3/users/" + userId + "?user_id_type=user_id")
                .header("Authorization", tenantAccessToken)
                .execute();
        @Data
        class FeiShuUserInfoRespBody {
            private Integer code;
            private String msg;
            private Object data;
        }
        FeiShuUserInfoRespBody feiShuUserInfoRespBody = JSONUtil.toBean(execute.body(), FeiShuUserInfoRespBody.class);
        if (feiShuUserInfoRespBody.getCode() != 0) {
            throw new LinkProcessException("用户: " + userId + " userId 检验失败");
        }
    }

    /**
     * 通过手机号获取用户id
     *
     * @param tenantAccessToken token
     * @param phones            手机号列表
     * @return 用户id列表
     */
    public List<String> getUserIdsByPhones(String tenantAccessToken, List<String> phones) {
        @Data
        class PhoneRequestBody {
            private List<String> mobiles;
        }
        PhoneRequestBody phoneRequestBody = new PhoneRequestBody();
        phoneRequestBody.setMobiles(phones);
        String body = JSONUtil.toJsonStr(phoneRequestBody);
        HttpResponse execute = HttpRequest.get("https://open.feishu.cn/open-apis/contact/v3/users/batch_get_id" + "?user_id_type=user_id")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute();
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
        FeiShuUserIdRespBody feiShuUserIdRespBody = JSONUtil.toBean(execute.body(), FeiShuUserIdRespBody.class);
        if (feiShuUserIdRespBody.code != 0) {
            throw new LinkProcessException("转换 userId 失败！！！");
        }
        return feiShuUserIdRespBody.data.user_list.stream().map(UserIdAndPhone::getMobile).collect(Collectors.toList());
    }
}
