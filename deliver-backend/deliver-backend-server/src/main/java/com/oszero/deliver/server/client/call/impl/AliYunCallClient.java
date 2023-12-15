package com.oszero.deliver.server.client.call.impl;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 电话客户端阿里云实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AliYunCallClient implements CallClient {

    private static final String ENDPOINT_OVERRIDE = "dyvmsapi.aliyuncs.com";
    private static final String OUT_ID = "outId";
    private static final String REGION = "region";
    private static final String CALLED_SHOW_NUMBER = "calledShowNumber";
    private static final String CALLED_NUMBER = "calledNumber";
    private static final String TTS_CODE = "ttsCode";
    private static final String TTS_PARAM = "ttsParam";
    private static final String PLAY_TIMES = "playTimes";
    private static final String VOLUME = "volume";
    private static final String SPEED = "speed";


    @Override
    public void sendCall(CallApp callApp, SendTaskDto sendTaskDto) {

        List<String> users = sendTaskDto.getUsers();
        String phoneNumbers = String.join(",", users);
        Map<String,Object> map = sendTaskDto.getParamMap();
        AliYunCallApp aliYunCallApp = (AliYunCallApp) callApp ;

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(aliYunCallApp.getAccessKeyId())
                .accessKeySecret(aliYunCallApp.getAccessKeySecret())
                .build());

        try (AsyncClient client = AsyncClient.builder()
                .region((String) map.get(REGION))
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(ENDPOINT_OVERRIDE)
                )
                .build()) {

            SingleCallByTtsRequest singleCallByTtsRequest = SingleCallByTtsRequest.builder()
                   .calledNumber((String) map.get(CALLED_NUMBER))
                    .calledShowNumber((String) map.get(CALLED_SHOW_NUMBER))
                    .ttsCode((String) map.get(TTS_CODE))
                    .ttsParam((String) map.get(TTS_PARAM))
                    .playTimes((Integer) map.get(PLAY_TIMES))
                    .volume((Integer) map.get(VOLUME))
                    .speed((Integer) map.get(SPEED))
                    .outId((String) map.get(OUT_ID))
                    .build();
            CompletableFuture<SingleCallByTtsResponse> response = client.singleCallByTts(singleCallByTtsRequest);
            SingleCallByTtsResponse resp = response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MessageException("语音电话失败，" + e.getMessage());
        }


    }


}

