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

package com.oszero.deliver.business.server.mq.consumer.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.handler.impl.*;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_RABBITMQ)
public class RabbitMQConsumer {

    private final CallHandler callHandler;
    private final SmsHandler smsHandler;
    private final MailHandler mailHandler;
    private final DingHandler dingHandler;
    private final WeChatHandler weChatHandler;
    private final FeiShuHandler feiShuHandler;
    private final OfficialAccountHandler officialAccountHandler;

    private final Producer producer;

    @RabbitListener(queues = MQConstant.CALL_QUEUE, ackMode = "MANUAL")
    public void onCallMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, callHandler);
    }

    @RabbitListener(queues = MQConstant.SMS_QUEUE, ackMode = "MANUAL")
    public void onSmsMessage(String message,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, smsHandler);
    }

    @RabbitListener(queues = MQConstant.MAIL_QUEUE, ackMode = "MANUAL")
    public void onMailMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, mailHandler);
    }

    @RabbitListener(queues = MQConstant.DING_QUEUE, ackMode = "MANUAL")
    public void onDingMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, dingHandler);
    }

    @RabbitListener(queues = MQConstant.WECHAT_QUEUE, ackMode = "MANUAL")
    public void onWeChatMessage(String message,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, weChatHandler);
    }

    @RabbitListener(queues = MQConstant.FEI_SHU_QUEUE, ackMode = "MANUAL")
    public void onFeiShuMessage(String message,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, feiShuHandler);
    }

    @RabbitListener(queues = MQConstant.OFFICIAL_ACCOUNT_QUEUE, ackMode = "MANUAL")
    public void onOfficialAccountMessage(String message,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, officialAccountHandler);
    }

    private void onMessageAck(long deliveryTag, Channel channel, String message, BaseHandler handler) throws Exception {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
            MQCommonConsumer.tryHandle(sendTaskDto, handler);
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            channel.basicAck(deliveryTag, false);
            // 报错重试
            MQCommonConsumer.catchHandle(sendTaskDto, exception, producer);
        }
    }

}
