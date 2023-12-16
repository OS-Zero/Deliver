package com.oszero.deliver.server.mq.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.util.RocketMQUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQProducer implements Producer {
    private final RocketMQUtils rocketMQUtils;
    private final MessageRecordService messageRecordService;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException(sendTaskDto, "[RocketMQProducer#sendMessage] 渠道类型配置错误");
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
            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息发送到 RocketMQ，消息已确认发送到消息队列");
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
        MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "RocketMQ 消息发送失败！！！");
        // 记录错误日志至info-log
        MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "RocketMQ 消息发送失败！！！");
        // 记录消息发送失败
        sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(sendTaskDto, StatusEnum.OFF, user));

        if (sendTaskDto.getRetry() > 0) {

            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "RocketMQ 重试消息已发送");
        } else {
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "RocketMQ 消息发送失败，重试次数已用完！！！");
            // 记录错误日志至info-log
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "RocketMQ 消息发送失败，重试次数已用完！！！");
        }
    }
}
