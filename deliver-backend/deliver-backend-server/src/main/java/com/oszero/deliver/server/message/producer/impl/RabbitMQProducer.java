package com.oszero.deliver.server.message.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.RabbitMQUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQProducer implements Producer {

    private final RabbitMQUtils rabbitMQUtils;

    @Override
    public void sendMessage(ChannelTypeEnum channelTypeEnum, SendTaskDto sendTaskDto) {
        switch (channelTypeEnum) {
            case CALL: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.CALL_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case SMS: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.SMS_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case MAIL: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.MAIL_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case WECHAT: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.WECHAT_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case DING: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.DING_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
            case FEI_SHU: {
                rabbitMQUtils.sendMessage(MQConstant.DELIVER_EXCHANGE, MQConstant.FEI_SHU_KEY_NAME, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
        }
    }
}
