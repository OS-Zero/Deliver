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

package com.oszero.deliver.business.server.mq.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.util.RedisUtils;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_REDIS)
public class RedisProducer implements Producer {

    private final RedisUtils redisUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[RedisProducer#sendMessage] 渠道类型配置错误");
        }
        String message = JSONUtil.toJsonStr(sendTaskDto);
        RecordId recordId = null;
        switch (channelTypeEnum) {
            case CALL -> recordId = redisUtils.sendMessage(MQConstant.CALL_STREAM, message);
            case SMS -> recordId = redisUtils.sendMessage(MQConstant.SMS_STREAM, message);
            case MAIL -> recordId = redisUtils.sendMessage(MQConstant.MAIL_STREAM, message);
            case DING -> recordId = redisUtils.sendMessage(MQConstant.DING_STREAM, message);
            case WECHAT -> recordId = redisUtils.sendMessage(MQConstant.WECHAT_STREAM, message);
            case FEI_SHU -> recordId = redisUtils.sendMessage(MQConstant.FEI_SHU_STREAM, message);
            default -> {
            }
        }
        if (Objects.isNull(recordId)) {
            retry(sendTaskDto);
        }
    }

    private void retry(SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetryCount() > 0) {
            sendTaskDto.setRetryCount(sendTaskDto.getRetryCount() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);
        }
    }
}
