package com.oszero.deliver.server.message.consumer.rocketmq;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.CallHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.CALL_TOPIC, consumerGroup = MQConstant.CALL_CONSUMER_GROUP)
public class CallConsumer implements RocketMQListener<MessageExt> {

    private final CallHandler callHandler;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[MailConsumer 接收到消息] {}", messageExt);

    }
}
