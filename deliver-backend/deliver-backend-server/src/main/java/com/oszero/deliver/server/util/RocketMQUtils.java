package com.oszero.deliver.server.util;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQUtils {

    private final RocketMQTemplate rocketMQTemplate;

    public SendResult sendMessage(String topic, String message) {
        return rocketMQTemplate.syncSend(topic, message);
    }
}
