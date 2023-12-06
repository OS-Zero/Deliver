package com.oszero.deliver.server.message.consumer.rocketmq;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.FeiShuHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 飞书 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.FEI_SHU_TOPIC, consumerGroup = MQConstant.FEI_SHU_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class FeiShuConsumer implements RocketMQListener<MessageExt> {

    private final FeiShuHandler feiShuHandler;
    private final RocketMQCommonConsumer rocketMQCommonConsumer;

    /**
     * 没有报错，就签收
     * 如果有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[FeiShuConsumer 接收到消息] {}", messageExt);
        rocketMQCommonConsumer.omMessageAck(messageExt, feiShuHandler);
    }
}
