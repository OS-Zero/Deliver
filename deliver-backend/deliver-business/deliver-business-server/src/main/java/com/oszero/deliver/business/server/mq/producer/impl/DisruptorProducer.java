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

import com.lmax.disruptor.dsl.Disruptor;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;
import com.oszero.deliver.business.server.mq.consumer.disruptor.DisruptorHandler;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.business.server.util.DisruptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_DISRUPTOR)
public class DisruptorProducer implements Producer {

    @Autowired
    @Qualifier(MQConstant.CALL_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.CallEventDisruptor> callEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.SMS_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.SmsEventDisruptor> smsEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.MAIL_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.MailEventDisruptor> mailEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.DING_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.DingEventDisruptor> dingEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.WECHAT_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.WeChatEventDisruptor> wechatEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.FEI_SHU_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.FeiShuEventDisruptor> feiShuEventDisruptor;

    @PostConstruct
    public void initializer() {
        DisruptorHandler.PRODUCER = this;
    }

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[DisruptorProducer#sendMessage] 渠道类型配置错误");
        }
        switch (channelTypeEnum) {
            case CALL -> DisruptorUtils.publishEvent(sendTaskDto, callEventDisruptor.getRingBuffer());
            case SMS -> DisruptorUtils.publishEvent(sendTaskDto, smsEventDisruptor.getRingBuffer());
            case MAIL -> DisruptorUtils.publishEvent(sendTaskDto, mailEventDisruptor.getRingBuffer());
            case DING -> DisruptorUtils.publishEvent(sendTaskDto, dingEventDisruptor.getRingBuffer());
            case WECHAT -> DisruptorUtils.publishEvent(sendTaskDto, wechatEventDisruptor.getRingBuffer());
            case FEI_SHU -> DisruptorUtils.publishEvent(sendTaskDto, feiShuEventDisruptor.getRingBuffer());
            default -> {
            }
        }
    }
}
