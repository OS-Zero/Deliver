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

import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author oszero
 * @version 1.0.0
 */
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
        callAsyncExecutor = buildThreadPoolTaskExecutor("callAsyncExecutor");
        return callAsyncExecutor;
    }

    @Bean("smsAsyncExecutor")
    public ThreadPoolTaskExecutor smsAsyncExecutor() {
        smsAsyncExecutor = buildThreadPoolTaskExecutor("smsAsyncExecutor");
        return smsAsyncExecutor;
    }

    @Bean("mailAsyncExecutor")
    public ThreadPoolTaskExecutor mailAsyncExecutor() {
        mailAsyncExecutor = buildThreadPoolTaskExecutor("mailAsyncExecutor");
        return mailAsyncExecutor;
    }

    @Bean("dingAsyncExecutor")
    public ThreadPoolTaskExecutor dingAsyncExecutor() {
        dingAsyncExecutor = buildThreadPoolTaskExecutor("dingAsyncExecutor");
        return dingAsyncExecutor;
    }

    @Bean("weChatAsyncExecutor")
    public ThreadPoolTaskExecutor weChatAsyncExecutor() {
        weChatAsyncExecutor = buildThreadPoolTaskExecutor("weChatAsyncExecutor");
        return weChatAsyncExecutor;
    }

    @Bean("feiShuAsyncExecutor")
    public ThreadPoolTaskExecutor feiShuAsyncExecutor() {
        feiShuAsyncExecutor = buildThreadPoolTaskExecutor("feiShuAsyncExecutor");
        return feiShuAsyncExecutor;
    }

    private ThreadPoolTaskExecutor buildThreadPoolTaskExecutor(String name) {
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
        executor.setThreadNamePrefix(name + "-");
        // 设置拒绝策略（当队列和最大线程数都满了的时候）
        // 以下是一个使用CallerRunsPolicy的示例，即由调用者所在线程来执行任务
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @PreDestroy
    public void destroy() {
        callAsyncExecutor.destroy();
        smsAsyncExecutor.destroy();
        mailAsyncExecutor.destroy();
        dingAsyncExecutor.destroy();
        weChatAsyncExecutor.destroy();
        feiShuAsyncExecutor.destroy();
    }
}
