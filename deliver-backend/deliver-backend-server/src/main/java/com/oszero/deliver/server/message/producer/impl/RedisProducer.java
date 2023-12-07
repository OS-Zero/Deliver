package com.oszero.deliver.server.message.producer.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.util.RedisUtils;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class RedisProducer implements Producer {

    private final RedisUtils redisUtils;

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "[RedisProducer#sendMessage] 渠道类型配置错误！！！"));
        }
        String message = JSONUtil.toJsonStr(sendTaskDto);
        RecordId recordId = null;
        switch (channelTypeEnum) {
            case CALL: {
                recordId = redisUtils.sendMessage(MQConstant.CALL_STREAM, message);
                break;
            }
            case SMS: {
                recordId = redisUtils.sendMessage(MQConstant.SMS_STREAM, message);
                break;
            }
            case MAIL: {
                recordId = redisUtils.sendMessage(MQConstant.MAIL_STREAM, message);
                break;
            }
            case DING: {
                recordId = redisUtils.sendMessage(MQConstant.DING_STREAM, message);
                break;
            }
            case WECHAT: {
                recordId = redisUtils.sendMessage(MQConstant.WECHAT_STREAM, message);
                break;
            }
            case FEI_SHU: {
                recordId = redisUtils.sendMessage(MQConstant.FEI_SHU_STREAM, message);
                break;
            }
        }
        if (Objects.isNull(recordId)) {
            retry(sendTaskDto);
        } else {
            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息发送到 Redis Stream，消息已确认发送到消息队列");
        }
    }

    /**
     * 重新发送消息
     *
     * @param sendTaskDto dto
     */
    private void retry(SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetry() > 0) {
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "Redis Stream 消息发送失败！！！");

            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            sendMessage(sendTaskDto);

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "Redis Stream 重试消息已发送");
        } else {
            // TODO:后续可监控告警上报
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "Redis Stream 消息发送失败，重试次数已用完，请检查 MQ 情况！！！"));
        }
    }
}
