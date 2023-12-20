/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.admin.client;

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

import java.util.Objects;

/**
 * App-飞书工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class AdminFeiShuClient {

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

        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
                .header("Content-Type", "application/json; charset=utf-8")
                .body(JSONUtil.toJsonStr(tenantAccessTokenReqBody))
                .execute()) {
            @Data
            class TenantAccessTokenRespBody {
                private Integer code;
                private String msg;
                private String tenant_access_token;
                private Integer expire;
            }
            String body = response.body();
            TenantAccessTokenRespBody tenantAccessTokenRespBody = JSONUtil.toBean(body, TenantAccessTokenRespBody.class);

            if (!Objects.equals(tenantAccessTokenRespBody.getCode(), 0)) {
                throw new BusinessException(tenantAccessTokenRespBody.getMsg());
            }
            log.info("获取飞书 tenantAccessToken 成功");
            return "Bearer " + tenantAccessTokenRespBody.getTenant_access_token();
        } catch (Exception e) {
            throw new BusinessException("获取飞书 tenantAccessToken 失败，" + e.getMessage() + "！！！");
        }
    }

    /**
     * 上传飞书文件
     *
     * @param tenantAccessToken token
     * @param platformFileDto   文件 dto
     * @return file_key
     */
    public String uploadFeiShuFile(String tenantAccessToken, PlatformFileDto platformFileDto) {

        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/files")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("file_type", platformFileDto.getFileType())
                .form("file_name", platformFileDto.getFileName())
                .form("file", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
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
                throw new BusinessException(feiShuUploadFileResponse.getMsg());
            }

            log.info("上传飞书文件成功");
            return feiShuUploadFileResponse.getData().getFile_key();
        } catch (Exception e) {
            throw new BusinessException("上传飞书文件失败，" + e.getMessage() + "！！！");
        }
    }

    /**
     * 上传飞书图片文件
     *
     * @param tenantAccessToken token
     * @param platformFileDto   文件 dto
     * @return file_key
     */
    public String uploadFeiShuImageFile(String tenantAccessToken, PlatformFileDto platformFileDto) {

        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/images")
                .header("Authorization", tenantAccessToken)
                .header("Content-Type", "multipart/form-data; boundary=---7MA4YWxkTrZu0gW")
                .form("image_type", "message")
                .form("image", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
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
                throw new BusinessException(feiShuUploadImageResponse.getMsg());
            }
            log.info("上传飞书图片成功");
            return feiShuUploadImageResponse.getData().getImage_key();
        } catch (Exception e) {
            throw new BusinessException("上传飞书图片失败，" + e.getMessage() + "！！！");
        }
    }
}
