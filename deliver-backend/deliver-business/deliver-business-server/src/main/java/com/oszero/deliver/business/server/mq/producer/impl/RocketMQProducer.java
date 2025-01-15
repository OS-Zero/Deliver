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
import com.oszero.deliver.business.server.util.RocketMQUtils;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_ROCKETMQ)
public class RocketMQProducer implements Producer {
    private final RocketMQUtils rocketMQUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[RocketMQProducer#sendMessage] 渠道类型配置错误");
        }
        SendResult sendResult = null;
        switch (channelTypeEnum) {
            case CALL -> sendResult = rocketMQUtils.sendMessage(MQConstant.CALL_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            case SMS -> sendResult = rocketMQUtils.sendMessage(MQConstant.SMS_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            case MAIL -> sendResult = rocketMQUtils.sendMessage(MQConstant.MAIL_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            case WECHAT -> sendResult = rocketMQUtils.sendMessage(MQConstant.WECHAT_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            case DING -> sendResult = rocketMQUtils.sendMessage(MQConstant.DING_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            case FEI_SHU -> sendResult = rocketMQUtils.sendMessage(MQConstant.FEI_SHU_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
            default -> {}
        }
        if (!Objects.isNull(sendResult) && Objects.equals(SendStatus.SEND_OK, sendResult.getSendStatus())) {
            //
        } else {
            retry(sendTaskDto);
        }
    }

    private void retry(SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetryCount() > 0) {
            sendTaskDto.setRetryCount(sendTaskDto.getRetryCount() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);
        }
    }
}
