package com.oszero.deliver.server.client.call.impl;


import cn.hutool.core.util.StrUtil;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dyvmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dyvmsapi20170525.models.SingleCallByTtsRequest;
import com.aliyun.sdk.service.dyvmsapi20170525.models.SingleCallByTtsResponse;
import com.oszero.deliver.server.client.call.CallClient;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.call.AliYunCallApp;
import com.oszero.deliver.server.model.app.call.CallApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 电话客户端阿里云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AliYunCallClient implements CallClient {

    private static final String ENDPOINT_OVERRIDE = "dyvmsapi.aliyuncs.com";
    private static final String REGION = "region";
    private static final String CALLED_SHOW_NUMBER = "calledShowNumber";
    private static final String TTS_CODE = "ttsCode";
    private static final String TTS_PARAM = "ttsParam";
    private static final String PLAY_TIMES = "playTimes";
    private static final String VOLUME = "volume";
    private static final String SPEED = "speed";
    private static final String OUT_ID = "outId";

    @Override
    public void sendCall(CallApp callApp, SendTaskDto sendTaskDto) {

        try {
            AliYunCallApp aliYunCallApp = (AliYunCallApp) callApp;

            List<String> users = sendTaskDto.getUsers();
            String phoneNumbers = users.get(0);
            // 获取阿里云语音通知相关参数
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String region = paramMap.get(REGION).toString();
            String calledShowNumber = paramMap.getOrDefault(CALLED_SHOW_NUMBER, "").toString();
            String ttsCode = paramMap.get(TTS_CODE).toString();
            String ttsParam = paramMap.getOrDefault(TTS_PARAM, "").toString();
            Integer playTimes = (Integer) paramMap.getOrDefault(PLAY_TIMES, -1);
            Integer volume = (Integer) paramMap.getOrDefault(VOLUME, -1);
            Integer speed = (Integer) paramMap.getOrDefault(SPEED, -1);
            String outId = paramMap.getOrDefault(OUT_ID, "").toString();

            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId(aliYunCallApp.getAccessKeyId())
                    .accessKeySecret(aliYunCallApp.getAccessKeySecret())
                    .build());

            try (AsyncClient client = AsyncClient.builder()
                    .region(region)
                    .credentialsProvider(provider)
                    .overrideConfiguration(ClientOverrideConfiguration.create()
                            .setEndpointOverride(ENDPOINT_OVERRIDE)
                    )
                    .build()) {

                SingleCallByTtsRequest.Builder builder = SingleCallByTtsRequest.builder()
                        .calledNumber(phoneNumbers);
                // 健壮性判断
                if (StrUtil.isNotBlank(calledShowNumber)) {
                    builder.calledShowNumber(calledShowNumber);
                }
                builder.ttsCode(ttsCode);
                if (StrUtil.isNotBlank(ttsParam)) {
                    builder.ttsParam(ttsParam);
                }
                if (!Objects.equals(playTimes, -1)) {
                    builder.playTimes(playTimes);
                }
                if (!Objects.equals(volume, -1)) {
                    builder.volume(volume);
                }
                if (!Objects.equals(speed, -1)) {
                    builder.speed(speed);
                }
                if (StrUtil.isNotBlank(outId)) {
                    builder.outId(outId);
                }

                SingleCallByTtsRequest singleCallByTtsRequest = builder.build();
                CompletableFuture<SingleCallByTtsResponse> response = client.singleCallByTts(singleCallByTtsRequest);
                SingleCallByTtsResponse resp = response.get();
            } catch (Exception e) {
                throw new MessageException(e.getMessage());
            }
        } catch (MessageException e) {
            throw new MessageException("阿里云语音电话失败，" + e.getMessage());
        }
    }
}

