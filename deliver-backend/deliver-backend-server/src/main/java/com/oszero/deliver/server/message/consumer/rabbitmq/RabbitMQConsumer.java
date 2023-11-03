package com.oszero.deliver.server.message.consumer.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.*;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RabbitMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final CallHandler callHandler;
    private final SmsHandler smsHandler;
    private final MailHandler mailHandler;
    private final DingHandler dingHandler;
    private final WeChatHandler weChatHandler;
    private final FeiShuHandler feiShuHandler;

    private final Producer producer;

    /**
     * 消费电话队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.CALL_QUEUE, ackMode = "MANUAL")
    public void onCallMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

    }

    /**
     * 消费短信队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.SMS_QUEUE, ackMode = "MANUAL")
    public void onSmsMessage(String message,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

    }

    /**
     * 消费邮件队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.MAIL_QUEUE, ackMode = "MANUAL")
    public void onMailMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
            mailHandler.doHandle(sendTaskDto);
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            // 第三个参数true，表示这个消息会重新进入队列
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                producer.sendMessage(sendTaskDto);
            }
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        }
    }

    /**
     * 消费钉钉队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.DING_QUEUE, ackMode = "MANUAL")
    public void onDingMessage(String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        SendTaskDto sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
        dingHandler.doHandle(sendTaskDto);
    }

    /**
     * 消费企业微信队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.WECHAT_QUEUE, ackMode = "MANUAL")
    public void onWeChatMessage(String message,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

    }

    /**
     * 消费飞书队列消息
     *
     * @param message     消息 json SendTaskDto
     * @param deliveryTag 消息标识
     * @param channel     与 RabbitMQ 连接的 Channel
     */
    @RabbitListener(queues = MQConstant.FEI_SHU_QUEUE, ackMode = "MANUAL")
    public void onFeiShuMessage(String message,
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
            feiShuHandler.doHandle(sendTaskDto);
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            // 第三个参数true，表示这个消息会重新进入队列
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                producer.sendMessage(sendTaskDto);
            }
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        }
    }
}
