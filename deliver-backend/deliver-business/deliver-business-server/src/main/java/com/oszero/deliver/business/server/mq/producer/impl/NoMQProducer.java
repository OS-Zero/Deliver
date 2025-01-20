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

import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.spring.*;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.business.server.util.ApplicationEventUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_NONE)
public class NoMQProducer implements Producer {

    private final ApplicationEventUtils applicationEventUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[NoMQProducer#sendMessage] 渠道类型配置错误");
        }
        switch (channelTypeEnum) {
            case CALL -> {
                CallEventSpring callEvent = new CallEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(callEvent);
            }
            case SMS -> {
                SmsEventSpring smsEvent = new SmsEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(smsEvent);
            }
            case MAIL -> {
                MailEventSpring mailEvent = new MailEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(mailEvent);
            }
            case DING -> {
                DingEventSpring dingEvent = new DingEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(dingEvent);
            }
            case WECHAT -> {
                WeChatEventSpring weChatEvent = new WeChatEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(weChatEvent);
            }
            case FEI_SHU -> {
                FeiShuEventSpring feiShuEvent = new FeiShuEventSpring(this, sendTaskDto);
                applicationEventUtils.publishCustomEvent(feiShuEvent);
            }
            default -> {
            }
        }
    }
}
