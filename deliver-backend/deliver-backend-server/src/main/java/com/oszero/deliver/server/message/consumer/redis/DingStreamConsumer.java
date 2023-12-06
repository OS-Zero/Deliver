package com.oszero.deliver.server.message.consumer.redis;

import com.oszero.deliver.server.message.consumer.handler.impl.DingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class DingStreamConsumer implements StreamListener<String, ObjectRecord<String, String>> {

    private final DingHandler dingHandler;
    private final StreamCommonConsumer streamCommonConsumer;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        log.info("[DingStreamConsumer 接收到消息] {}", message);
        streamCommonConsumer.omMessageAck(message, dingHandler);
    }
}
