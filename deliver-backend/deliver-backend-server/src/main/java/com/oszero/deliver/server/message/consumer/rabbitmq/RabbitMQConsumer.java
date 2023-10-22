package com.oszero.deliver.server.message.consumer.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.*;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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

    @RabbitListener(queues = MQConstant.CALL_QUEUE)
    public void onCallMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.MAIL_QUEUE)
    public void onMailMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.SMS_QUEUE)
    public void onSmsMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.DING_QUEUE)
    public void onDingMessage(String message) throws Exception {
        SendTaskDto sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
        dingHandler.doHandle(sendTaskDto);
    }

    @RabbitListener(queues = MQConstant.WECHAT_QUEUE)
    public void onWeChatMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.FEI_SHU_QUEUE)
    public void onFeiShuMessage(String message) throws Exception {
        SendTaskDto sendTaskDto = JSONUtil.toBean(message, SendTaskDto.class);
        feiShuHandler.doHandle(sendTaskDto);
    }
}
