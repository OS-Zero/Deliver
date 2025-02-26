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

package com.oszero.deliver.business.server.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;

/**
 * @author oszero
 * @version 1.0.0
 */
public class SendTaskUtils {

    public static String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    public static void retryHandle(SendTaskDto sendTaskDto) {
        sendTaskDto.setMessageId(generateMessageId());
        sendTaskDto.setRetryCount(sendTaskDto.getRetryCount() + 1);
    }

    public static void setSendTaskDto(SendTaskDto sendTaskDto) {
        ThreadLocalUtils.setSendTaskDto(sendTaskDto);
    }

    public static SendTaskDto getSendTaskDto() {
        return ThreadLocalUtils.getSendTaskDto();
    }

    public static String toMessage(SendTaskDto sendTaskDto) {
        return JSONUtil.toJsonStr(sendTaskDto);
    }

    public static SendTaskDto fromMessage(String message) {
        return JSONUtil.toBean(message, SendTaskDto.class);
    }

    public static void clear() {
        ThreadLocalUtils.clearSendTaskDto();
    }
}
