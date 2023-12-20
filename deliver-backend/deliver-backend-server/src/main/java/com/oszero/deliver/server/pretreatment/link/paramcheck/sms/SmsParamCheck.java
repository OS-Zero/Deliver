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

package com.oszero.deliver.server.pretreatment.link.paramcheck.sms;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.enums.SmsProviderTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * 短信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SmsParamCheck implements MessageLink<SendTaskDto> {

    private final Map<String, ParamStrategy> paramStrategyMap;
    private final static HashSet<String> SMS_PROVIDER_SET = new HashSet<>(Arrays.asList(
            SmsProviderTypeEnum.ALI_YUN.getName(),
            SmsProviderTypeEnum.TENCENT.getName()
    ));

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String smsProvider = paramMap.getOrDefault("smsProvider", "").toString();
        if (StrUtil.isBlank(smsProvider)) {
            throw new MessageException(sendTaskDto, "短信 smsProvider 参数为空");
        }
        if (!SMS_PROVIDER_SET.contains(smsProvider)) {
            throw new MessageException(sendTaskDto, "短信 smsProvider 非法，必须为 " + SMS_PROVIDER_SET);
        }

        try {
            String strategyName = ParamStrategy.SMS_STRATEGY_BEAN_PRE_NAME + smsProvider;
            ParamStrategy paramStrategy = paramStrategyMap.get(strategyName);
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "短信消息参数校验失败，" + e.getMessage());
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成短信消息参数校验");
    }
}
