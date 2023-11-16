package com.oszero.deliver.admin.util.app;

import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.app.DingApp;
import com.taobao.api.ApiException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
            throw new BusinessException("获取 AccessToken 失败");
        }

        DingAccessTokenBody dingAccessTokenBody = JSONUtil.toBean(response.getBody(), DingAccessTokenBody.class);
        return dingAccessTokenBody.getAccessToken();
    }
}
