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

package com.oszero.deliver.server.client.sms.factory;

import com.oszero.deliver.server.client.sms.SmsClient;
import com.oszero.deliver.server.client.sms.impl.AliYunSmsClient;
import com.oszero.deliver.server.client.sms.impl.TencentSmsClient;
import com.oszero.deliver.server.enums.SmsProviderTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * SmsFactory
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SmsFactory {

    private final AliYunSmsClient aliYunSmsClient;
    private final TencentSmsClient tencentSmsClient;

    /**
     * 获取客户端通过 smsProvider
     *
     * @param smsProvider 提供商
     * @return 客户端
     */
    public SmsClient getClient(String smsProvider) {
        if (SmsProviderTypeEnum.ALI_YUN.getName().equals(smsProvider)) {
            return aliYunSmsClient;
        } else if (SmsProviderTypeEnum.TENCENT.getName().equals(smsProvider)) {
            return tencentSmsClient;
        } else {
            throw new MessageException("暂时不提供指定短信服务提供商：" + smsProvider);
        }
    }
}
