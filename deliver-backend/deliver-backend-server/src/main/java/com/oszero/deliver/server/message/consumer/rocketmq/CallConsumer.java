package com.oszero.deliver.server.message.consumer.rocketmq;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.CallHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 电话 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.CALL_TOPIC, consumerGroup = MQConstant.CALL_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class CallConsumer implements RocketMQListener<MessageExt> {

    private final CallHandler callHandler;
    private final RocketMQCommonConsumer rocketMQCommonConsumer;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[CallConsumer 接收到消息] {}", messageExt);
        rocketMQCommonConsumer.omMessageAck(messageExt, callHandler);
    }
}
