/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.server.config;

import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.mq.consumer.redis.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class RedisStreamConfig {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisConnectionFactory factory;

    private final CallStreamConsumer callStreamConsumer;
    private final SmsStreamConsumer smsStreamConsumer;
    private final MailStreamConsumer mailStreamConsumer;
    private final DingStreamConsumer dingStreamConsumer;
    private final WeChatStreamConsumer weChatStreamConsumer;
    private final FeiShuStreamConsumer feiShuStreamConsumer;

    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> callListenerContainer;
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> smsListenerContainer;
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> mailListenerContainer;
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> dingListenerContainer;
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> weChatListenerContainer;
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> feiShuListenerContainer;

    @PostConstruct
    public void init() {
        try {
            createStreamAndGroup();
            initListenerContainer();
        } catch (Exception ignore) {}
    }

    public void createStreamAndGroup() {
        buildStream(MQConstant.CALL_STREAM, MQConstant.CALL_STREAM_CONSUMER_GROUP);
        buildStream(MQConstant.SMS_STREAM, MQConstant.SMS_STREAM_CONSUMER_GROUP);
        buildStream(MQConstant.MAIL_STREAM, MQConstant.MAIL_STREAM_CONSUMER_GROUP);
        buildStream(MQConstant.DING_STREAM, MQConstant.DING_STREAM_CONSUMER_GROUP);
        buildStream(MQConstant.WECHAT_STREAM, MQConstant.WECHAT_STREAM_CONSUMER_GROUP);
        buildStream(MQConstant.FEI_SHU_STREAM, MQConstant.FEI_SHU_STREAM_CONSUMER_GROUP);
    }

    public void buildStream(String streamKey, String consumerGroupName) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(streamKey))) {
            StreamInfo.XInfoGroups groups = stringRedisTemplate.opsForStream().groups(streamKey);
            if (groups.isEmpty()) {
                stringRedisTemplate.opsForStream().createGroup(streamKey, consumerGroupName);
            }
        } else {
            stringRedisTemplate.opsForStream().createGroup(streamKey, consumerGroupName);
        }
    }

    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> buildOptions() {
        return StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                .pollTimeout(Duration.ofSeconds(3))
                .batchSize(1)
                .serializer(new StringRedisSerializer())
                .executor(Executors.newFixedThreadPool(10))
                .errorHandler(t -> {})
                .targetType(String.class).build();
    }

    public void initListenerContainer() throws Exception {

        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> callListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> smsListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> mailListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> dingListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> weChatListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> feiShuListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());

        callListenerContainer.receive(Consumer.from(MQConstant.CALL_STREAM_CONSUMER_GROUP, MQConstant.CALL_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.CALL_STREAM, ReadOffset.lastConsumed()),
                callStreamConsumer);
        smsListenerContainer.receive(Consumer.from(MQConstant.SMS_STREAM_CONSUMER_GROUP, MQConstant.SMS_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.SMS_STREAM, ReadOffset.lastConsumed()),
                smsStreamConsumer);
        mailListenerContainer.receive(Consumer.from(MQConstant.MAIL_STREAM_CONSUMER_GROUP, MQConstant.MAIL_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.MAIL_STREAM, ReadOffset.lastConsumed()),
                mailStreamConsumer);
        dingListenerContainer.receive(Consumer.from(MQConstant.DING_STREAM_CONSUMER_GROUP, MQConstant.DING_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.DING_STREAM, ReadOffset.lastConsumed()),
                dingStreamConsumer);
        weChatListenerContainer.receive(Consumer.from(MQConstant.WECHAT_STREAM_CONSUMER_GROUP, MQConstant.WECHAT_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.WECHAT_STREAM, ReadOffset.lastConsumed()),
                weChatStreamConsumer);
        feiShuListenerContainer.receive(Consumer.from(MQConstant.FEI_SHU_STREAM_CONSUMER_GROUP, MQConstant.FEI_SHU_STREAM_CONSUMER_NAME + InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(MQConstant.FEI_SHU_STREAM, ReadOffset.lastConsumed()),
                feiShuStreamConsumer);

        callListenerContainer.start();
        smsListenerContainer.start();
        mailListenerContainer.start();
        dingListenerContainer.start();
        weChatListenerContainer.start();
        feiShuListenerContainer.start();

        this.callListenerContainer = callListenerContainer;
        this.smsListenerContainer = smsListenerContainer;
        this.mailListenerContainer = mailListenerContainer;
        this.dingListenerContainer = dingListenerContainer;
        this.weChatListenerContainer = weChatListenerContainer;
        this.feiShuListenerContainer = feiShuListenerContainer;
    }

    @PreDestroy
    public void destroy() {
        this.callListenerContainer.stop();
        this.smsListenerContainer.stop();
        this.mailListenerContainer.stop();
        this.dingListenerContainer.stop();
        this.weChatListenerContainer.stop();
        this.feiShuListenerContainer.stop();
    }

}
