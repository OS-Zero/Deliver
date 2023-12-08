package com.oszero.deliver.server.message.consumer.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.message.consumer.handler.impl.*;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * StreamCommonConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class StreamCommonConsumer {
    private final MessageRecordService messageRecordService;
    private final Producer producer;
    private final StringRedisTemplate stringRedisTemplate;

    public void omMessageAck(ObjectRecord<String, String> message, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            String next = message.getValue();

            sendTaskDto = JSONUtil.toBean(next, SendTaskDto.class);

            // 记录链路追踪 id
            String traceId = sendTaskDto.getTraceId();
            if (StrUtil.isBlank(traceId)) {
                throw new MessageException(sendTaskDto, "traceId 为空");
            }
            MDCUtils.put(TraceIdConstant.TRACE_ID, traceId);

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "接收到 Redis Stream 消息，消息已送达消费者");

            handler.doHandle(sendTaskDto);
            // 确认消息消费成功
            ack(message, handler);
        } catch (Exception exception) {
            // 确认消息消费成功
            ack(message, handler);
            if (!Objects.isNull(sendTaskDto)) {
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog(exception.getMessage());

                // 记录消息消费失败
                SendTaskDto finalSendTaskDto = sendTaskDto;
                sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(finalSendTaskDto, StatusEnum.OFF, user));

                if (sendTaskDto.getRetry() > 0) {

                    // 重新发送
                    sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                    sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                    producer.sendMessage(sendTaskDto);
                    MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "Redis Stream 重试消息已发送");
                } else {
                    MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "Redis Stream 消息发送失败，重试次数已用完！！！");
                }
            } else {
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog("消息消费失败，" + exception.getMessage() + "！！！");
            }
        }
    }

    private void ack(ObjectRecord<String, String> message, BaseHandler handler) {
        // 消息确认
        Long acknowledge = 0L;
        if (handler instanceof CallHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.CALL_STREAM,
                    MQConstant.CALL_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof SmsHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.SMS_STREAM,
                    MQConstant.SMS_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof MailHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.MAIL_STREAM,
                    MQConstant.MAIL_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof DingHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.DING_STREAM,
                    MQConstant.DING_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof WeChatHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.WECHAT_STREAM,
                    MQConstant.WECHAT_STREAM_CONSUMER_GROUP,
                    message.getId());
        } else if (handler instanceof FeiShuHandler) {
            acknowledge = stringRedisTemplate.opsForStream().acknowledge(MQConstant.FEI_SHU_STREAM,
                    MQConstant.FEI_SHU_STREAM_CONSUMER_GROUP,
                    message.getId());
        }
        if (Objects.equals(acknowledge, 1L)) {
            log.info("Redis Stream 消息已确认消费");
        } else {
            log.error("Redis Stream 消息确认失败");
        }
    }
}
