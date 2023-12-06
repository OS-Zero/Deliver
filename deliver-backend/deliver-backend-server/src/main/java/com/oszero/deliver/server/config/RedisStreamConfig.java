package com.oszero.deliver.server.config;

import com.oszero.deliver.server.constant.MQConstant;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 配置 Redis Stream
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "redis")
public class RedisStreamConfig implements ApplicationRunner {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        // 创建Stream（如果不存在）
        try {

            stringRedisTemplate.opsForStream().createGroup(MQConstant.CALL_STREAM, MQConstant.CALL_STREAM_CONSUMER_GROUP); // 创建电话消费者组
            stringRedisTemplate.opsForStream().createGroup(MQConstant.SMS_STREAM, MQConstant.SMS_STREAM_CONSUMER_GROUP); // 创建短信消费者组
            stringRedisTemplate.opsForStream().createGroup(MQConstant.MAIL_STREAM, MQConstant.MAIL_STREAM_CONSUMER_GROUP); // 创建邮箱消费者组
            stringRedisTemplate.opsForStream().createGroup(MQConstant.DING_STREAM, MQConstant.DING_STREAM_CONSUMER_GROUP); // 创建钉钉消费者组
            stringRedisTemplate.opsForStream().createGroup(MQConstant.WECHAT_STREAM, MQConstant.WECHAT_STREAM_CONSUMER_GROUP); // 创建企微消费者组
            stringRedisTemplate.opsForStream().createGroup(MQConstant.FEI_SHU_STREAM, MQConstant.FEI_SHU_STREAM_CONSUMER_GROUP); // 创建飞书消费者组
            log.info("初始化 Redis Stream 成功");
        } catch (RedisCommandExecutionException e) {
            // Stream已经存在，不处理异常
            log.error("初始化 Redis Stream 失败，请检查 Redis！！！");
        }
    }
}
