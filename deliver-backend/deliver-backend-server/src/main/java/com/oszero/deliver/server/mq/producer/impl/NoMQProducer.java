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

import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.model.event.*;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.util.ApplicationEventUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 无 MQ 生产者
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class NoMQProducer implements Producer {

    private final ApplicationEventUtils applicationEventUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException(sendTaskDto, "[NoMQProducer#sendMessage] 渠道类型配置错误");
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息事件发布到 Spring，消息事件已确认发布到 Spring");
        switch (channelTypeEnum) {
            case CALL -> {
                CallEvent callEvent = new CallEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(callEvent);
            }
            case SMS -> {
                SmsEvent smsEvent = new SmsEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(smsEvent);
            }
            case MAIL -> {
                MailEvent mailEvent = new MailEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(mailEvent);
            }
            case DING -> {
                DingEvent dingEvent = new DingEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(dingEvent);
            }
            case WECHAT -> {
                WeChatEvent weChatEvent = new WeChatEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(weChatEvent);
            }
            case FEI_SHU -> {
                FeiShuEvent feiShuEvent = new FeiShuEvent(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(feiShuEvent);
            }
            default -> {
            }
        }
    }
}
