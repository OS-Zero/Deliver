package com.oszero.deliver.server.client.call.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.client.call.CallClient;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.call.CallApp;
import com.oszero.deliver.server.model.app.call.TencentCallApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vms.v20200902.VmsClient;
import com.tencentcloudapi.vms.v20200902.models.SendStatus;
import com.tencentcloudapi.vms.v20200902.models.SendTtsVoiceRequest;
import com.tencentcloudapi.vms.v20200902.models.SendTtsVoiceResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 腾讯云电话客户端
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class TencentCallClient implements CallClient {
    @Override
    public void sendCall(CallApp callApp, SendTaskDto sendTaskDto) throws Exception {
        try {
            TencentCallApp tencentCallApp = (TencentCallApp) callApp;
            String secretId = tencentCallApp.getSecretId();
            String secretKey = tencentCallApp.getSecretKey();
            Credential cred = new Credential(secretId, secretKey);

            List<String> users = sendTaskDto.getUsers();
            // 获取腾讯云语音通知相关参数
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String region = paramMap.get("region").toString();
            String templateId = paramMap.get("templateId").toString();
            List<String> templateParamSet = (List<String>) paramMap.getOrDefault("templateParamSet", new ArrayList<String>());
            Long playTimes = (Long) paramMap.getOrDefault("playTimes", -1L);
            String sessionContext = paramMap.getOrDefault("sessionContext", "").toString();
            String voiceSdkAppid = paramMap.get("voiceSdkAppid").toString();

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            VmsClient client = new VmsClient(cred, region, clientProfile);

            SendTtsVoiceRequest req = new SendTtsVoiceRequest();
            // 设置腾讯云语音电话所需参数
            req.setTemplateId(templateId);
            if (CollectionUtil.isNotEmpty(templateParamSet)) {
                String[] templateParamSetArray = templateParamSet.toArray(new String[0]);
                req.setTemplateParamSet(templateParamSetArray);
            }
            req.setCalledNumber(users.get(0));
            if (!Objects.equals(playTimes, -1L)) {
                req.setPlayTimes(playTimes);
            }
            if (StrUtil.isNotBlank(sessionContext)) {
                req.setSessionContext(sessionContext);
            }
            req.setVoiceSdkAppid(voiceSdkAppid);

            SendTtsVoiceResponse resp = client.SendTtsVoice(req);
            SendStatus sendStatus = resp.getSendStatus();

        } catch (Exception e) {
            throw new MessageException("腾讯云语音电话消息发送失败，" + e.getMessage());
        }
    }
}
