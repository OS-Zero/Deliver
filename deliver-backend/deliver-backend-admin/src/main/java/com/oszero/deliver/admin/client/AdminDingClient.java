package com.oszero.deliver.admin.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.app.DingApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
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
public class AdminDingClient {

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
                throw new BusinessException(dingAccessTokenBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new BusinessException("获取钉钉 accessToken 失败，" + e.getMessage() + "！！！");
        }
        log.info("获取钉钉 accessToken 成功");
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

        DingBody dingBody;
        try (HttpResponse response = HttpRequest.post("https://oapi.dingtalk.com/media/upload?access_token=" + accessToken)
                .header("Content-Type", "multipart/form-data")
                .form("type", platformFileDto.getFileType())
                .form("media", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {

            dingBody = JSONUtil.toBean(response.body(), DingBody.class);

            if (!Objects.equals(dingBody.getErrcode(), 0)) {
                throw new BusinessException(dingBody.getErrmsg());
            }
        } catch (Exception e) {
            throw new BusinessException("上传钉钉文件失败，" + e.getMessage() + "！！！");
        }
        log.info("上传钉钉文件成功");
        return dingBody.getMediaId();

    }
}
