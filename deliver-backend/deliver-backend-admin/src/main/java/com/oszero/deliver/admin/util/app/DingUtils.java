package com.oszero.deliver.admin.util.app;

import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.app.DingApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 获取模版详情
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
            throw new BusinessException("获取 AccessToken 失败");
        }

        DingAccessTokenBody dingAccessTokenBody = JSONUtil.toBean(response.getBody(), DingAccessTokenBody.class);
        return dingAccessTokenBody.getAccessToken();
    }

    /**
     * 上传钉钉文件
     *
     * @param accessToken     token
     * @param platformFileDto 文件 dto
     * @return media_id
     */
    public String uploadDingFile(String accessToken, PlatformFileDto platformFileDto) {
        @Data
        class DingBody {
            private Integer errcode;
            private String errmsg;
            private String mediaId;
            private String createdAt;
            private String type;

        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(platformFileDto.getFileType());
        byte[] file = platformFileDto.getFile();
        FileItem item = new FileItem(platformFileDto.getFileName(), file);
        req.setMedia(item);
        OapiMediaUploadResponse rsp = null;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException a) {
            throw new BusinessException("钉钉上传失败");
        }

        DingBody dingBody = JSONUtil.toBean(rsp.getBody(), DingBody.class);
        if (dingBody.getErrcode() != 0) {
            throw new BusinessException("钉钉上传失败");
        }

        return dingBody.getMediaId();
    }
}
