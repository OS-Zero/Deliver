package com.oszero.deliver.admin.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.admin.model.app.FeiShuApp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}
