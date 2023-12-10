package com.oszero.deliver.server.mq.consumer.redis;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.handler.impl.*;
import com.oszero.deliver.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
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
            sendTaskDto = JSONUtil.toBean(message.getValue(), SendTaskDto.class);
            MQCommonConsumer.tryHandle(sendTaskDto, handler);

            // 确认消息消费成功
            ack(message, handler);
        } catch (Exception exception) {
            // 确认消息消费成功
            ack(message, handler);
            MQCommonConsumer.catchHandle(sendTaskDto, exception, messageRecordService, producer);
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
