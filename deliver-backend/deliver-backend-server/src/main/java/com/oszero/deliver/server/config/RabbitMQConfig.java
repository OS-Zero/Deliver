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
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@ConditionalOnProperty(value = "mq-type", havingValue = "rabbitmq")
public class RabbitMQConfig {

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder.directExchange(MQConstant.DELIVER_EXCHANGE).build();
    }

    @Bean
    public Queue getDingQueue() {
        return QueueBuilder.durable(MQConstant.DING_QUEUE).build();
    }

    @Bean
    public Queue getFeiShuQueue() {
        return QueueBuilder.durable(MQConstant.FEI_SHU_QUEUE).build();
    }

    @Bean
    public Queue getWeChatQueue() {
        return QueueBuilder.durable(MQConstant.WECHAT_QUEUE).build();
    }

    @Bean
    public Queue getMailQueue() {
        return QueueBuilder.durable(MQConstant.MAIL_QUEUE).build();
    }

    @Bean
    public Queue getSmsQueue() {
        return QueueBuilder.durable(MQConstant.SMS_QUEUE).build();
    }

    @Bean
    public Queue getCallQueue() {
        return QueueBuilder.durable(MQConstant.CALL_QUEUE).build();
    }

    @Bean
    public Binding getDingBinding() {
        return BindingBuilder.bind(getDingQueue()).to(getExchange()).with(MQConstant.DING_KEY_NAME).noargs();
    }

    @Bean
    public Binding getFeiShuBinding() {
        return BindingBuilder.bind(getFeiShuQueue()).to(getExchange()).with(MQConstant.FEI_SHU_KEY_NAME).noargs();
    }

    @Bean
    public Binding getWeChatBinding() {
        return BindingBuilder.bind(getWeChatQueue()).to(getExchange()).with(MQConstant.WECHAT_KEY_NAME).noargs();
    }

    @Bean
    public Binding getMailBinding() {
        return BindingBuilder.bind(getMailQueue()).to(getExchange()).with(MQConstant.MAIL_KEY_NAME).noargs();
    }

    @Bean
    public Binding getSmsBinding() {
        return BindingBuilder.bind(getSmsQueue()).to(getExchange()).with(MQConstant.SMS_KEY_NAME).noargs();
    }

    @Bean
    public Binding getCallBinding() {
        return BindingBuilder.bind(getCallQueue()).to(getExchange()).with(MQConstant.CALL_KEY_NAME).noargs();
    }
}
