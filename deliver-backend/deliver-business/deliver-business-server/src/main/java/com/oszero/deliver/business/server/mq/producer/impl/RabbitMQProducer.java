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
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.business.server.util.RabbitMQUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_RABBITMQ)
public class RabbitMQProducer implements Producer {

    private final RabbitMQUtils rabbitMQUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[RabbitMQProducer#sendMessage] 渠道类型配置错误");
        }
        String message = JSONUtil.toJsonStr(sendTaskDto);
        CorrelationData correlationData = new CorrelationData(sendTaskDto.getTraceId() + "&&&" + sendTaskDto.getTemplateId());
        switch (channelTypeEnum) {
            case CALL ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.CALL_KEY_NAME, message, correlationData);
            case SMS ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.SMS_KEY_NAME, message, correlationData);
            case MAIL ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.MAIL_KEY_NAME, message, correlationData);
            case WECHAT ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.WECHAT_KEY_NAME, message, correlationData);
            case DING ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.DING_KEY_NAME, message, correlationData);
            case FEI_SHU ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.FEI_SHU_KEY_NAME, message, correlationData);
            case OFFICIAL_ACCOUNT ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.OFFICIAL_ACCOUNT_KEY_NAME, message, correlationData);
            default -> {
            }
        }
    }
}
