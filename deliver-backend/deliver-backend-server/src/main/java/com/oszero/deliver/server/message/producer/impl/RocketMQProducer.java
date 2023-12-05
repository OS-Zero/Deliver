package com.oszero.deliver.server.message.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.RocketMQUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RocketMQ 生产者
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQProducer implements Producer {
    private final RocketMQUtils rocketMQUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new BusinessException("");
        }
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
                sendResult = rocketMQUtils.sendMessage(MQConstant.FEI_SHU_TOPIC, JSONUtil.toJsonStr(sendTaskDto));
                break;
            }
        }
        if (!Objects.isNull(sendResult) && Objects.equals(SendStatus.SEND_OK, sendResult.getSendStatus())) {
            log.info("模板 ID " + sendTaskDto.getTemplateId() + " RocketMQ 消息发送成功");
        } else {
            // 消息再次重试发送
            retry(sendTaskDto);
        }
    }

    /**
     * 重新发送消息
     *
     * @param sendTaskDto dto
     */
    private void retry(SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetry() > 0) {
            log.error("模板 ID " + sendTaskDto.getTemplateId() + " 再次发送消息，消息重试发送");
            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);
        } else {
            throw new MessageException("RocketMQ 消息发送失败，重试次数已用完，请检查 MQ 情况！！！");
        }
    }
}
