package com.oszero.deliver.admin.util.app;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.app.FeiShuApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
import lombok.AllArgsConstructor;
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
     * 上传飞书文件
     *
     * @param tenantAccessToken token
     * @param platformFileDto   文件 dto
     * @return file_key
     */
    public String uploadFeiShuFile(String tenantAccessToken, PlatformFileDto platformFileDto) {
        HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/files")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("file_type", platformFileDto.getFileType())
                .form("file_name", platformFileDto.getFileName())
                .form("file", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute();
        @Data
        class FeiShuUploadFileResponse {
            private Integer code;
            private String msg;
            private Data data;

            @lombok.Data
            class Data {
                private String file_key;
            }
        }
        FeiShuUploadFileResponse feiShuUploadFileResponse = JSONUtil.toBean(response.body(), FeiShuUploadFileResponse.class);
        if (!feiShuUploadFileResponse.getCode().equals(0) || StrUtil.isBlank(feiShuUploadFileResponse.getData().getFile_key())) {
            throw new BusinessException("上传飞书文件失败！！！");
        }
        return feiShuUploadFileResponse.getData().getFile_key();
    }

    /**
     * 上传飞书图片文件
     *
     * @param tenantAccessToken token
     * @param platformFileDto   文件 dto
     * @return file_key
     */
    public String uploadFeiShuImageFile(String tenantAccessToken, PlatformFileDto platformFileDto) {
        HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/images")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("image_type", "message")
                .form("image", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute();
        @Data
        class FeiShuUploadImageResponse {
            private Integer code;
            private String msg;
            private Data data;

            @lombok.Data
            class Data {
                private String image_key;
            }
        }
        FeiShuUploadImageResponse feiShuUploadImageResponse = JSONUtil.toBean(response.body(), FeiShuUploadImageResponse.class);
        if (!feiShuUploadImageResponse.getCode().equals(0) || StrUtil.isBlank(feiShuUploadImageResponse.getData().getImage_key())) {
            throw new BusinessException("上传飞书图片失败！！！");
        }
        return feiShuUploadImageResponse.getData().getImage_key();
    }
}
