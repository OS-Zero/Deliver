package com.oszero.deliver.server.mq.consumer.rocketmq;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.handler.impl.WeChatHandler;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 企业微信 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.WECHAT_TOPIC, consumerGroup = MQConstant.WECHAT_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class WeChatConsumer implements RocketMQListener<MessageExt> {

    private final WeChatHandler weChatHandler;
    private final RocketMQCommonConsumer rocketMQCommonConsumer;

    /**
     * 没有报错，就签收
     * 如果有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        rocketMQCommonConsumer.omMessageAck(messageExt, weChatHandler);
    }
}
