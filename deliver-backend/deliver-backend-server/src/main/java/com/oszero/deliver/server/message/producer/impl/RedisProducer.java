package com.oszero.deliver.server.message.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Redis 生产者
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class RedisProducer implements Producer {

    private final RedisUtils redisUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new BusinessException("[RabbitMQProducer#sendWorkNoticeMessage] 渠道类型配置错误！！！");
        }
        String traceId = sendTaskDto.getTraceId();
        String message = JSONUtil.toJsonStr(sendTaskDto);
        RecordId recordId = null;
        switch (channelTypeEnum) {
            case CALL: {
                recordId = redisUtils.sendMessage(MQConstant.CALL_STREAM, traceId, message);
                break;
            }
            case SMS: {
                recordId = redisUtils.sendMessage(MQConstant.SMS_STREAM, traceId, message);
                break;
            }
            case MAIL: {
                recordId = redisUtils.sendMessage(MQConstant.MAIL_STREAM, traceId, message);
                break;
            }
            case DING: {
                recordId = redisUtils.sendMessage(MQConstant.DING_STREAM, traceId, message);
                break;
            }
            case WECHAT: {
                recordId = redisUtils.sendMessage(MQConstant.WECHAT_STREAM, traceId, message);
                break;
            }
            case FEI_SHU: {
                recordId = redisUtils.sendMessage(MQConstant.FEI_SHU_STREAM, traceId, message);
                break;
            }
        }
        if (Objects.isNull(recordId)) {
            retry(sendTaskDto);
        } else {
            log.info("模板 ID " + sendTaskDto.getTemplateId() + " Redis Stream 消息发送成功");
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
