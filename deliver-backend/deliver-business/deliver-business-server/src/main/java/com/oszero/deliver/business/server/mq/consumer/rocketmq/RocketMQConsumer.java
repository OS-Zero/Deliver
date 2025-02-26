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

package com.oszero.deliver.business.server.mq.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.handler.impl.*;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.consumer.common.MQCommonConsumerHandler;
import com.oszero.deliver.business.server.mq.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
public class RocketMQConsumer {

    private final Producer producer;

    public void omMessageAck(MessageExt messageExt, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(new String(messageExt.getBody(), StandardCharsets.UTF_8), SendTaskDto.class);
            MQCommonConsumerHandler.tryHandle(sendTaskDto, handler);
        } catch (Exception exception) {
            MQCommonConsumerHandler.catchHandle(sendTaskDto, exception, producer);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.CALL_TOPIC, consumerGroup = MQConstant.CALL_CONSUMER_GROUP)
    public static class CallConsumer implements RocketMQListener<MessageExt> {

        private final CallHandler callHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, callHandler);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.SMS_TOPIC, consumerGroup = MQConstant.SMS_CONSUMER_GROUP)
    public static class SmsConsumer implements RocketMQListener<MessageExt> {

        private final SmsHandler smsHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, smsHandler);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.MAIL_TOPIC, consumerGroup = MQConstant.MAIL_CONSUMER_GROUP)
    public static class MailConsumer implements RocketMQListener<MessageExt> {

        private final MailHandler mailHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, mailHandler);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.DING_TOPIC, consumerGroup = MQConstant.DING_CONSUMER_GROUP)
    public static class DingConsumer implements RocketMQListener<MessageExt> {

        private final DingHandler dingHandler;
        private final RocketMQConsumer commonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            commonConsumer.omMessageAck(messageExt, dingHandler);
        }

    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.WECHAT_TOPIC, consumerGroup = MQConstant.WECHAT_CONSUMER_GROUP)
    public static class WeChatConsumer implements RocketMQListener<MessageExt> {

        private final WeChatHandler weChatHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, weChatHandler);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.FEI_SHU_TOPIC, consumerGroup = MQConstant.FEI_SHU_CONSUMER_GROUP)
    public static class FeiShuConsumer implements RocketMQListener<MessageExt> {

        private final FeiShuHandler feiShuHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, feiShuHandler);
        }
    }

    @Component
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
    @RocketMQMessageListener(topic = MQConstant.OFFICIAL_ACCOUNT_TOPIC, consumerGroup = MQConstant.OFFICIAL_ACCOUNT_CONSUMER_GROUP)
    public static class OfficialAccountConsumer implements RocketMQListener<MessageExt> {

        private final OfficialAccountHandler officialAccountHandler;
        private final RocketMQConsumer rocketMQCommonConsumer;

        @Override
        public void onMessage(MessageExt messageExt) {
            rocketMQCommonConsumer.omMessageAck(messageExt, officialAccountHandler);
        }
    }
}
