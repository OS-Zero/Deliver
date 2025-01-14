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

package com.oszero.deliver.business.admin.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.model.dto.request.messagetemplate.SendMessageRequestDto;
import com.oszero.deliver.business.common.model.common.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class SendMessageUtils {

    @Value("${deliver-server.url}")
    private String serverUrl;

    public void sendMessage(SendMessageRequestDto sendMessageRequestDto) {
        CommonResult<?> commonResult;
        try (HttpResponse response = HttpRequest.post(serverUrl)
                .header("ContentType", "application/json")
                .body(JSONUtil.toJsonStr(sendMessageRequestDto))
                .execute()) {
            commonResult = JSONUtil.toBean(response.body(), CommonResult.class);
        } catch (Exception e) {
            throw new BusinessException("发送接口失败，服务端异常，请检查");
        }
        if (Objects.isNull(commonResult)) {
            throw new BusinessException("发送接口失败，服务端异常，请检查");
        }
        if (!commonResult.getCode().equals(0)) {
            throw new BusinessException(commonResult.getErrorMessage());
        }
    }
}
