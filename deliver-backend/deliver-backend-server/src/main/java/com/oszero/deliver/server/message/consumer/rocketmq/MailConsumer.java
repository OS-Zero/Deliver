package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.MailHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 邮箱 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.MAIL_TOPIC, consumerGroup = MQConstant.MAIL_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class MailConsumer implements RocketMQListener<MessageExt> {

    private final MailHandler mailHandler;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[MailConsumer 接收到消息] {}", messageExt);
        SendTaskDto sendTaskDto = JSONUtil.toBean(
                new String(messageExt.getBody(), StandardCharsets.UTF_8
                ), SendTaskDto.class);
        mailHandler.doHandle(sendTaskDto);
    }
}
