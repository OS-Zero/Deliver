package com.oszero.deliver.server.message.consumer.rabbitmq;

import com.oszero.deliver.server.constant.MQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQConsumer {

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
    public void onDingMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.WECHAT_QUEUE)
    public void onWeChatMessage(String message) {

    }

    @RabbitListener(queues = MQConstant.FEI_SHU_QUEUE)
    public void onFeiShuMessage(String message) {

    }
}
