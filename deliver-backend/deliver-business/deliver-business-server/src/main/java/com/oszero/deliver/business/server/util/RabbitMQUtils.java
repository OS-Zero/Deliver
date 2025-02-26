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

package com.oszero.deliver.business.server.util;

import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.mq.producer.MessageCallback;
import lombok.Getter;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_RABBITMQ)
public class RabbitMQUtils implements RabbitTemplate.ConfirmCallback {
    private final RabbitTemplate rabbitTemplate;
    private MessageCallback messageCallback;

    public RabbitMQUtils(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void initMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void sendMessage(String exchange, String routingKey, String messageId, String message) {
        // 使用关联数据发送消息
        CorrelationData correlationData = new CustomCorrelationData(messageId, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    // 消息成功到达Exchange时触发
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData instanceof CustomCorrelationData) {
            messageCallback.messageCallback(correlationData.getId(), ((CustomCorrelationData) correlationData).getOriginalMessage(), ack);
        }
    }

    // 自定义 CorrelationData 扩展类，用于存储原始消息
    @Getter
    public static class CustomCorrelationData extends CorrelationData {
        private final String originalMessage;
        public CustomCorrelationData(String messageId, String originalMessage) {
            super(messageId);
            this.originalMessage = originalMessage;
        }
    }
}
