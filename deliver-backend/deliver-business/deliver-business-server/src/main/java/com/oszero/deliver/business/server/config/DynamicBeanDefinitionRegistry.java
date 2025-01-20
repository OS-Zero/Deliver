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

import com.oszero.deliver.business.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.business.server.pretreatment.link.convert.Phone2UserIdConvert;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
public class DynamicBeanDefinitionRegistry {

    @Bean
    public ApplicationContextInitializer<ConfigurableApplicationContext> initializer() {
        return applicationContext -> {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
            registerPhone2UserIdBean(registry);
        };
    }

    private void registerPhone2UserIdBean(BeanDefinitionRegistry registry) {
        registerBean(registry, PretreatmentCodeConstant.CONVERT_PRE + PretreatmentCodeConstant.PHONE_DING, Phone2UserIdConvert.DingStrategy.class);
        registerBean(registry, PretreatmentCodeConstant.CONVERT_PRE + PretreatmentCodeConstant.PHONE_WECHAT, Phone2UserIdConvert.WeChatStrategy.class);
        registerBean(registry, PretreatmentCodeConstant.CONVERT_PRE + PretreatmentCodeConstant.PHONE_FEI_SHU, Phone2UserIdConvert.FeiShuStrategy.class);
    }

    private void registerBean(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
