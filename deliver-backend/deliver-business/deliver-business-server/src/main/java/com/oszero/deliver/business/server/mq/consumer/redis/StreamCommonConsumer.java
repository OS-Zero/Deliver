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

package com.oszero.deliver.business.server.mq.consumer.redis;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.handler.impl.*;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.business.server.mq.producer.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class StreamCommonConsumer {
    private final Producer producer;
    private final StringRedisTemplate stringRedisTemplate;

    public void omMessageAck(ObjectRecord<String, String> message, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(message.getValue(), SendTaskDto.class);
            MQCommonConsumer.tryHandle(sendTaskDto, handler);
            ack(message, handler);
        } catch (Exception exception) {
            ack(message, handler);
            MQCommonConsumer.catchHandle(sendTaskDto, exception, producer);
        }
    }

    private void ack(ObjectRecord<String, String> message, BaseHandler handler) {
        // 消息确认
        Long acknowledge = 0L;
        if (handler instanceof CallHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.CALL_STREAM,
                    MQConstant.CALL_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof SmsHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.SMS_STREAM,
                    MQConstant.SMS_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof MailHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.MAIL_STREAM,
                    MQConstant.MAIL_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof DingHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.DING_STREAM,
                    MQConstant.DING_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof WeChatHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.WECHAT_STREAM,
                    MQConstant.WECHAT_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof FeiShuHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.FEI_SHU_STREAM,
                    MQConstant.FEI_SHU_STREAM_CONSUMER_GROUP,
                    message.getId());
        }
    }
}
