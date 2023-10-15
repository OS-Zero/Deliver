package com.oszero.deliver.server.message.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.RocketMQUtils;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQProducer implements Producer {
    private final RocketMQUtils rocketMQUtils;

    @Override
    public boolean sendMessage(ChannelTypeEnum channelTypeEnum, SendTaskDto sendTaskDto) {
        SendResult sendResult = null;
        switch (channelTypeEnum) {
            case CALL: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.CALL_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case SMS: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.SMS_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case MAIL: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.MAIL_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case WECHAT: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.WECHAT_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case DING: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.DING_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case FEI_SHU: {
                sendResult = rocketMQUtils.sendMessage(MQConstant.FEISHU_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
        }
        return !Objects.isNull(sendResult) && SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }
}
