package com.oszero.deliver.server.message.consumer.redis;

import com.oszero.deliver.server.message.consumer.handler.impl.FeiShuHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * Redis 消费者
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class FeiShuStreamConsumer implements StreamListener<String, ObjectRecord<String, String>> {

    private final FeiShuHandler feiShuHandler;
    private final StreamCommonConsumer streamCommonConsumer;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        streamCommonConsumer.omMessageAck(message, feiShuHandler);
    }
}
