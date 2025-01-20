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

package com.oszero.deliver.platformclient.client.call.factory;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.platformclient.client.call.CallClient;
import com.oszero.deliver.platformclient.client.call.impl.AliYunCallClient;
import com.oszero.deliver.platformclient.client.call.impl.TencentCallClient;
import com.oszero.deliver.platformclient.common.ClientConstant;
import com.oszero.deliver.platformclient.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CallFactory {
    private final AliYunCallClient aliYunCallClient;
    private final TencentCallClient tencentCallClient;

    public CallClient getClient(String callProviderTypeName) {
        if (StrUtil.equals(ClientConstant.ALI_YUN, callProviderTypeName)) {
            return aliYunCallClient;
        } else if (StrUtil.equals(ClientConstant.TENCENT, callProviderTypeName)) {
            return tencentCallClient;
        } else {
            throw new ClientException("暂时不提供指定短信服务提供商：" + callProviderTypeName);
        }
    }
}
