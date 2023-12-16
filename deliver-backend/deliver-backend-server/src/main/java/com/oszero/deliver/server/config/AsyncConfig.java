package com.oszero.deliver.server.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class AsyncConfig {

    private ThreadPoolTaskExecutor callAsyncExecutor;
    private ThreadPoolTaskExecutor smsAsyncExecutor;
    private ThreadPoolTaskExecutor mailAsyncExecutor;
    private ThreadPoolTaskExecutor dingAsyncExecutor;
    private ThreadPoolTaskExecutor weChatAsyncExecutor;
    private ThreadPoolTaskExecutor feiShuAsyncExecutor;

    @Bean("callAsyncExecutor")
    public ThreadPoolTaskExecutor callAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("callAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.callAsyncExecutor = executor;
        return executor;
    }

    @Bean("smsAsyncExecutor")
    public ThreadPoolTaskExecutor smsAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("smsAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.smsAsyncExecutor = executor;
        return executor;
    }

    @Bean("mailAsyncExecutor")
    public ThreadPoolTaskExecutor mailAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("mailAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.mailAsyncExecutor = executor;
        return executor;
    }

    @Bean("dingAsyncExecutor")
    public ThreadPoolTaskExecutor dingAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("dingAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.dingAsyncExecutor = executor;
        return executor;
    }

    @Bean("weChatAsyncExecutor")
    public ThreadPoolTaskExecutor weChatAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("weChatAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.weChatAsyncExecutor = executor;
        return executor;
    }

    @Bean("feiShuAsyncExecutor")
    public ThreadPoolTaskExecutor feiShuAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（最小线程数）
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间（单位：秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程池名前缀
        executor.setThreadNamePrefix("feiShuAsyncExecutor-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        this.feiShuAsyncExecutor = executor;
        return executor;
    }

    @PreDestroy
    public void destroy() {

        // 关闭线程池
        this.callAsyncExecutor.destroy();
        this.smsAsyncExecutor.destroy();
        this.mailAsyncExecutor.destroy();
        this.dingAsyncExecutor.destroy();
        this.weChatAsyncExecutor.destroy();
        this.feiShuAsyncExecutor.destroy();
        log.info("关闭 SpringBoot 线程池");
    }
}
