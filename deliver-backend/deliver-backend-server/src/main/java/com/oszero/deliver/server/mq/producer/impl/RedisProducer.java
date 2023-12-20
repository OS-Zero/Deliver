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

package com.oszero.deliver.server.mq.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.util.RedisUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Redis 生产者
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class RedisProducer implements Producer {

    private final RedisUtils redisUtils;
    private final MessageRecordService messageRecordService;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException(sendTaskDto, "[RedisProducer#sendMessage] 渠道类型配置错误");
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
        }
        if (Objects.isNull(recordId)) {
            retry(sendTaskDto);
        } else {
            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息发送到 Redis Stream，消息已确认发送到消息队列");
        }
    }

    /**
     * 重新发送消息
     *
     * @param sendTaskDto dto
     */
    private void retry(SendTaskDto sendTaskDto) {
        MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "Redis Stream 消息发送失败！！！");
        // 记录错误日志至info-log
        MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "Redis Stream 消息发送失败！！！");
        // 记录消息发送失败
        sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(sendTaskDto, StatusEnum.OFF, user));

        if (sendTaskDto.getRetry() > 0) {

            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "Redis Stream 重试消息已发送");
        } else {
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "Redis Stream 消息发送失败，重试次数已用完！！！");
            // 记录错误日志至info-log
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "Redis Stream 消息发送失败，重试次数已用完！！！");
        }
    }
}
