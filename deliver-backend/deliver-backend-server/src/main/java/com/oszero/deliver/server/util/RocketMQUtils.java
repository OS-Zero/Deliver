package com.oszero.deliver.server.util;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RocketMQUtils {
    private final RocketMQTemplate rocketMQTemplate;

    public SendResult sendMessage(String topic, String message) {
        return rocketMQTemplate.syncSend(topic, message);
    }
}
