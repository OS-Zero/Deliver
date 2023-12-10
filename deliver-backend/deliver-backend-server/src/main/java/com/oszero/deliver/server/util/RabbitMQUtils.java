package com.oszero.deliver.server.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RabbitMQ 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQUtils {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQUtils(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置 ConfirmCallback 以处理确认和退回
        this.rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            SendTaskDto sendTaskDto = null;
            if (correlationData != null) {
                String[] split = correlationData.getId().split("&&&");
                MDCUtils.put(TraceIdConstant.TRACE_ID, split[0]);

                ReturnedMessage returned = correlationData.getReturned();
                if (!Objects.isNull(returned)) { // 不为 null 代表发送失败
                    sendTaskDto = JSONUtil.toBean(new String(returned.getMessage().getBody()), SendTaskDto.class);
                }
                if (ack && Objects.isNull(sendTaskDto)) {

                    // 消息已成功发送到交换机
                    sendTaskDto = new SendTaskDto();
                    sendTaskDto.setTraceId(split[0]);
                    sendTaskDto.setTemplateId(Long.valueOf(split[1]));
                    MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息发送到 RabbitMQ，消息已确认发送到消息队列");
                } else {
                    // 处理消息发送失败的情况
                    if (sendTaskDto != null) {
                        String exchange = correlationData.getReturned().getExchange();
                        String routingKey = correlationData.getReturned().getRoutingKey();
                        retry(exchange, routingKey, sendTaskDto);
                    }
                }
                MDCUtils.clear();
            }
        });
    }

    public void sendMessage(String exchange, String routingKey, String message, CorrelationData correlationData) {
        // 使用关联数据发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    public void retry(String exchange, String routingKey, SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetry() > 0) {

            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "RabbitMQ 消息发送失败！！！");

            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            String message = JSONUtil.toJsonStr(sendTaskDto);

            // 指定关联数据（消息的唯一标识符），这里发送一条新的数据，所以与之前的不一样
            CorrelationData correlationData = new CorrelationData(sendTaskDto.getTraceId() + "&&&" + sendTaskDto.getTemplateId() + "&&&" + UUID.randomUUID());
            sendMessage(exchange, routingKey, message, correlationData);

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "RabbitMQ 重试消息已发送");
        } else {
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "RabbitMQ 消息发送失败，重试次数已用完！！！");
        }
    }
}
