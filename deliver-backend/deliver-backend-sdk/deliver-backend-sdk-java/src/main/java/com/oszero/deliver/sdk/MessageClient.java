package com.oszero.deliver.sdk;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.sdk.exception.BusinessException;
import com.oszero.deliver.sdk.model.CommonResult;
import com.oszero.deliver.sdk.model.dto.SendRequestDto;

import java.util.Objects;

public class MessageClient {

    private final String host;

    public MessageClient(String host) {
        this.host = host;
    }

    public CommonResult<?> sendMessage(SendRequestDto sendRequestDto) {
        CommonResult<?> commonResult;
        try {
            HttpResponse response = HttpRequest.post(this.host)
                    .header("ContentType", "application/json")
                    .body(JSONUtil.toJsonStr(sendRequestDto))
                    .execute();
            commonResult = JSONUtil.toBean(response.body(), CommonResult.class);
            if (Objects.isNull(commonResult) || !commonResult.getCode().equals(200)) {
                throw new BusinessException("发送消息失败！！！");
            }
        } catch (Exception exception) {
            throw new BusinessException("发送消息失败！！！");
        }
        return commonResult;
    }
}
