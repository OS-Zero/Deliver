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

import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.impl.OfficialAccountHandler;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.OFFICIAL_ACCOUNT_TOPIC, consumerGroup = MQConstant.OFFICIAL_ACCOUNT_CONSUMER_GROUP)
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
public class OfficialAccountConsumer implements RocketMQListener<MessageExt> {

    private final OfficialAccountHandler officialAccountHandler;
    private final RocketMQCommonConsumer rocketMQCommonConsumer;

    @Override
    public void onMessage(MessageExt messageExt) {
        rocketMQCommonConsumer.omMessageAck(messageExt, officialAccountHandler);
    }
}
