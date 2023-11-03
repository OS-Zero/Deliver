package com.oszero.deliver.server.message.consumer.rocketmq;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.DingHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 钉钉 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.DING_TOPIC, consumerGroup = MQConstant.DING_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class DingConsumer implements RocketMQListener<MessageExt> {

    private final DingHandler dingHandler;
    private final CommonConsumer commonConsumer;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[DingConsumer 接收到消息] {}", messageExt);
        commonConsumer.omMessageAck(messageExt, dingHandler);
    }

}
