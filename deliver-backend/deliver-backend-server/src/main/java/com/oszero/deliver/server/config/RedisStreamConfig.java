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

package com.oszero.deliver.server.config;

import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.mq.consumer.redis.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 配置 Redis Stream
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
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

    /**
     * 初始化 Stream
     */
    @PostConstruct
    public void init() {
        try {
            // 1.创建Stream（如果不存在）
            createStreamAndGroup();
            // 2.监听器初始化
            initListenerContainer();

            log.info("初始化 Redis Stream 成功");
        } catch (Exception e) {
            // Stream已经存在，不处理异常
            log.error("初始化 Redis Stream 失败，请检查 Redis！！！");
        }
    }

    /**
     * 创建 Stream
     */
    public void createStreamAndGroup() {
        // 创建电话消费者组
        buildStream(MQConstant.CALL_STREAM, MQConstant.CALL_STREAM_CONSUMER_GROUP);
        // 创建短信消费者组
        buildStream(MQConstant.SMS_STREAM, MQConstant.SMS_STREAM_CONSUMER_NAME);
        // 创建邮箱消费者组
        buildStream(MQConstant.MAIL_STREAM, MQConstant.MAIL_STREAM_CONSUMER_NAME);
        // 创建钉钉消费者组
        buildStream(MQConstant.DING_STREAM, MQConstant.DING_STREAM_CONSUMER_NAME);
        // 创建企微消费者组
        buildStream(MQConstant.WECHAT_STREAM, MQConstant.WECHAT_STREAM_CONSUMER_NAME);
        // 创建飞书消费者组
        buildStream(MQConstant.FEI_SHU_STREAM, MQConstant.FEI_SHU_STREAM_CONSUMER_NAME);
    }

    /**
     * 构建 Stream
     *
     * @param streamKey         streamKey
     * @param consumerGroupName consumerGroupName
     */
    public void buildStream(String streamKey, String consumerGroupName) {
        /*
         * 这里必须先判空，重复创建组会报错，获取不存在的key的组也会报错
         * 所以需要先判断是否存在key，在判断是否存在组
         */
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(streamKey))) {
            StreamInfo.XInfoGroups groups = stringRedisTemplate.opsForStream().groups(streamKey);
            if (groups.isEmpty()) {
                stringRedisTemplate.opsForStream().createGroup(streamKey, consumerGroupName); // 创建消费者组
            }
        } else {
            stringRedisTemplate.opsForStream().createGroup(streamKey, consumerGroupName); // 创建消费者组
        }
    }

    /**
     * 构建监听器配置项
     *
     * @return StreamMessageListenerContainerOptions
     */
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> buildOptions() {
        return StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                //block读取超时时间
                .pollTimeout(Duration.ofSeconds(3))
                //count 数量（一次只获取一条消息）
                .batchSize(1)
                //序列化规则
                .serializer(new StringRedisSerializer())
                //TODO：后续使用自定义线程池
                .executor(Executors.newFixedThreadPool(10))
                //错误处理
                .errorHandler(t -> log.error("Redis 消息推送错误，请检查 Redis！！！"))
                //目标类型
                .targetType(String.class).build();
    }

    /**
     * 开启监听器接收消息
     */
    public void initListenerContainer() throws Exception {

        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> callListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> smsListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> mailListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> dingListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> weChatListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());
        final StreamMessageListenerContainer<String, ObjectRecord<String, String>> feiShuListenerContainer = StreamMessageListenerContainer.create(factory, buildOptions());

        //注册消费者 消费者名称，从哪条消息开始消费，消费者类
        // > 表示没消费过的消息
        // $ 表示最新的消息
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

    /**
     * 优雅关闭监听器
     */
    @PreDestroy
    public void destroy() {
        this.callListenerContainer.stop();
        this.smsListenerContainer.stop();
        this.mailListenerContainer.stop();
        this.dingListenerContainer.stop();
        this.weChatListenerContainer.stop();
        this.feiShuListenerContainer.stop();
        log.info("Redis 监听容器销毁成功");
    }

}
