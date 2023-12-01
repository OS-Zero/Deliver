package com.oszero.deliver.admin.util.app;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.app.WeChatApp;
import com.oszero.deliver.admin.model.dto.app.PlatformFileDto;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道-企业微信工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class WeChatUtils {

    /**
     * 获取企业微信 Token
     *
     * @param weChatApp 企微 APP
     * @return Token
     */
    public String getAccessToken(WeChatApp weChatApp) {

        String corpid = weChatApp.getCorpid();
        String corpsecret = weChatApp.getCorpsecret();
        @Data
        class WeChatResponse {
            private Integer errcode;
            private String errmsg;
            private String access_token;
            private Integer expires_in;
        }

        WeChatResponse weChatResponse;
        try (HttpResponse response = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret)
                .execute()) {

            weChatResponse = JSONUtil.toBean(response.body(), WeChatResponse.class);
            if (!Objects.equals(weChatResponse.getErrcode(), 0)) {
                throw new BusinessException("获取企业微信 Token 失败：" + weChatResponse.getErrmsg());
            }
        } catch (Exception e) {
            throw new BusinessException("企业微信获取 Token 接口调用失败！！！");
        }
        log.info("获取企微 Token 成功！");
        return weChatResponse.getAccess_token();
    }

    /**
     * 上传企微文件
     *
     * @param accessToken     token
     * @param platformFileDto 文件 dto
     * @return media_id
     */
    public String uploadWeChatFile(String accessToken, PlatformFileDto platformFileDto) {
        String fileType = platformFileDto.getFileType();
        @Data
        class WechatResponse {
            private Integer errcode;
            private String errmsg;
            private String type;
            private String media_id;
            private String created_at;
        }

        try (HttpResponse response = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=" + fileType)
                .header("Content-Type", "multipart/form-data; boundary=-------------------------acebdf13572468")
                .form("media", platformFileDto.getFile(), platformFileDto.getFileName())
                .execute()) {
            WechatResponse wechatResponse = JSONUtil.toBean(response.body(), WechatResponse.class);
            if (!Objects.equals(wechatResponse.getErrcode(), 0)) {
                throw new BusinessException("上传企业微信平台文件失败，" + wechatResponse.getErrmsg() + "！！！");
            }
            return wechatResponse.getMedia_id();
        } catch (Exception e) {
            throw new BusinessException("上传企业微信平台文件出错！！！");
        }
    }
}
