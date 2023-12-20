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

package com.oszero.deliver.server.mq.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * RocketMQ 通用消费者
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQCommonConsumer {

    private final MessageRecordService messageRecordService;
    private final Producer producer;

    public void omMessageAck(MessageExt messageExt, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(new String(messageExt.getBody(), StandardCharsets.UTF_8), SendTaskDto.class);
            MQCommonConsumer.tryHandle(sendTaskDto, handler);
        } catch (Exception exception) {
            MQCommonConsumer.catchHandle(sendTaskDto, exception, messageRecordService, producer);
        }
    }

}
