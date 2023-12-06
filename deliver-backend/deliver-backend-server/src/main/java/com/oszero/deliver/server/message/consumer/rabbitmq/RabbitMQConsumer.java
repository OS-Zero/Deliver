package com.oszero.deliver.server.message.consumer.rabbitmq;

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
import com.oszero.deliver.server.web.service.MessageRecordService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQConsumer {

    /**
     * 各处理器
     */
    private final CallHandler callHandler;
    private final SmsHandler smsHandler;
    private final MailHandler mailHandler;
    private final DingHandler dingHandler;
    private final WeChatHandler weChatHandler;
    private final FeiShuHandler feiShuHandler;

    /**
     * 消息记录服务
     */
    private final MessageRecordService messageRecordService;

    /**
     * 生产者
     */
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
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, callHandler);
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
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, smsHandler);
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
        onMessageAck(deliveryTag, channel, message, mailHandler);
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
        onMessageAck(deliveryTag, channel, message, dingHandler);
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
                                @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        onMessageAck(deliveryTag, channel, message, weChatHandler);
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
        onMessageAck(deliveryTag, channel, message, feiShuHandler);
    }

    private void onMessageAck(long deliveryTag, Channel channel, String message, BaseHandler handler) throws Exception {

        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);

            // 记录链路追踪 id
            String traceId = sendTaskDto.getTraceId();
            if (StrUtil.isBlank(traceId)) {
                throw new MessageException("traceId 为空！！！");
            }
            MDCUtils.put(TraceIdConstant.TRACE_ID, traceId);

            // 获取整个调用栈
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            // 获取类名和方法名
            String className = this.getClass().getName();
            String methodName = stackTrace[2].getMethodName();
            log.info("[{}#{} 接收到消息] {}", className, methodName, message);

            handler.doHandle(sendTaskDto);
            // RabbitMQ 的 ack 机制中，第二个参数返回 true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            channel.basicAck(deliveryTag, false);
            // 报错重试
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                // 记录消息消费失败
                SendTaskDto finalSendTaskDto = sendTaskDto;
                sendTaskDto.getUsers()
                        .forEach(
                                user -> messageRecordService.saveMessageRecord(finalSendTaskDto, StatusEnum.OFF, user)
                        );

                // 重新发送
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                producer.sendMessage(sendTaskDto);
            }
        }
    }

}
