package com.oszero.deliver.server.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQUtils {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQUtils(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置ConfirmCallback以处理确认和退回
        this.rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            SendTaskDto sendTaskDto = null;
            if (correlationData != null) {
                ReturnedMessage returned = correlationData.getReturned();
                if (!Objects.isNull(returned)) { // 不为 null 代表发送失败
                    sendTaskDto = JSONUtil.toBean(new String(returned.getMessage().getBody()), SendTaskDto.class);
                }
                if (ack && Objects.isNull(sendTaskDto)) {
                    String[] split = correlationData.getId().split("&&&");
                    MDCUtils.put(TraceIdConstant.TRACE_ID, split[0]);
                    // 消息已成功发送到交换机
                    log.info("模板 ID " + split[1] + " RabbitMQ 消息发送成功");
                } else {
                    // 处理消息发送失败的情况
                    if (sendTaskDto != null) {
                        String exchange = correlationData.getReturned().getExchange();
                        String routingKey = correlationData.getReturned().getRoutingKey();
                        retry(exchange, routingKey, sendTaskDto);
                    }
                }
            }
        });
    }

    public void sendMessage(String exchange, String routingKey, String message, CorrelationData correlationData) {
        // 使用关联数据发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    public void retry(String exchange, String routingKey, SendTaskDto sendTaskDto) {
        if (sendTaskDto.getRetry() > 0) {
            log.error("模板 ID " + sendTaskDto.getTemplateId() + " 再次发送消息，消息重试发送");
            sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
            sendTaskDto.setRetried(1);
            String message = JSONUtil.toJsonStr(sendTaskDto);

            // 指定关联数据（消息的唯一标识符）
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

            sendMessage(exchange, routingKey, message, correlationData);
        } else {
            log.error("RabbitMQ 消息发送失败，重试次数已用完，请检查 MQ 情况！！！");
        }
    }
}
