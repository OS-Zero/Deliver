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

package com.oszero.deliver.business.server.mq.producer;

import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.util.SendTaskUtils;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface Producer {

    /**
     * 发送消息
     */
    void sendMessage(SendTaskDto sendTaskDto);

    default ChannelTypeEnum getChannelTypeEnum(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("渠道类型配置错误");
        }
        return channelTypeEnum;
    }

    default void messageCallback(SendTaskDto sendTaskDto, boolean success) {
        if (success) {
            // 发送成功
        } else {
            retrySendMessage(sendTaskDto);
        }
    }

    /**
     * 重试消息
     */
    default void retrySendMessage(SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetryCount() >= sendTaskDto.getRetryMaxCount()) {
            return;
        }
        SendTaskUtils.retryHandle(sendTaskDto);
        sendMessage(sendTaskDto);
    }
}
