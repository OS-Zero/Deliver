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

    /**
     *
     * @param accessToken
     * @param platformFileDto
     * @return       media_id
     */

    public String  uploadDingFile (String accessToken, PlatformFileDto platformFileDto){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(platformFileDto.getFileType());
        byte[] file = platformFileDto.getFile();
        FileItem item = new FileItem(platformFileDto.getFileName(),file);
        req.setMedia(item);
        OapiMediaUploadResponse rsp =null;
        try {
              rsp = client.execute(req, accessToken);
        }catch (ApiException a){
            throw new BusinessException("钉钉上传失败");
        }

       if(rsp.getErrcode()!=0){
           throw new BusinessException("钉钉上传失败");
       }
       return rsp.getMediaId();
    }
}
