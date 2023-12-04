package com.oszero.deliver.admin.util.app;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
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

import java.util.Objects;

/**
 * App-钉钉工具类
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

        String appKey = dingApp.getAppKey();
        String appSecret = dingApp.getAppSecret();

        @Data
        class DingAccessTokenBody {

            private Integer errcode;
            private String accessToken;
            private String errmsg;
            private Integer expiresIn;

        }

        DingAccessTokenBody dingAccessTokenBody;
        try (HttpResponse response = HttpRequest.get("https://oapi.dingtalk.com/gettoken?appkey=" + appKey + "&appsecret=" + appSecret)
                .execute()) {

            dingAccessTokenBody = JSONUtil.toBean(response.body(), DingAccessTokenBody.class);

            if (!Objects.equals(dingAccessTokenBody.getErrcode(), 0)) {
                throw new BusinessException("获取钉钉 Token 失败：" + dingAccessTokenBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new BusinessException("钉钉获取 Token 接口调用失败！！！");
        }

        log.info("获取钉钉 Token 成功！");
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
