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

package com.oszero.deliver.business.server.mq.producer.impl.rabbitmq;

import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.producer.MessageCallback;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.business.server.util.RabbitMQUtils;
import com.oszero.deliver.business.server.util.SendTaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_RABBITMQ)
public class RabbitMQProducer implements Producer, MessageCallback {

    private final RabbitMQUtils rabbitMQUtils;

    @PostConstruct
    public void init() {
        rabbitMQUtils.initMessageCallback(this);
    }

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        String messageId = sendTaskDto.getMessageId();
        String message = SendTaskUtils.toMessageByEncrypt(sendTaskDto);
        switch (getChannelTypeEnum(sendTaskDto)) {
            case CALL ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.CALL_KEY_NAME, messageId, message);
            case SMS ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.SMS_KEY_NAME, messageId, message);
            case MAIL ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.MAIL_KEY_NAME, messageId, message);
            case WECHAT ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.WECHAT_KEY_NAME, messageId, message);
            case DING ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.DING_KEY_NAME, messageId, message);
            case FEI_SHU ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.FEI_SHU_KEY_NAME, messageId, message);
            case OFFICIAL_ACCOUNT ->
                    rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.OFFICIAL_ACCOUNT_KEY_NAME, messageId, message);
            default -> {
            }
        }
    }

    @Override
    public void messageCallback(String messageId, Object message, boolean success) {
        SendTaskDto sendTaskDto = SendTaskUtils.fromMessageByDecrypt((String) message);
        messageCallback(sendTaskDto, success);
    }
}
