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

package com.oszero.deliver.business.server.mq.consumer.common;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.constant.MDCConstant;
import com.oszero.deliver.business.common.util.MDCUtils;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.mq.producer.Producer;

/**
 * @author oszero
 * @version 1.0.0
 */
public class MQCommonConsumer {

    public static void tryHandle(SendTaskDto sendTaskDto, BaseHandler handler) throws Exception {
        String traceId = sendTaskDto.getTraceId();
        if (StrUtil.isBlank(traceId)) {
            throw new MessageException("traceId为空");
        }
        MDCUtils.put(MDCConstant.TRANCE_ID_NAME, traceId);
        handler.doHandle(sendTaskDto);
    }

    public static void catchHandle(SendTaskDto sendTaskDto, Exception exception, Producer producer) {
        producer.retrySendMessage(sendTaskDto);
        MDCUtils.clear();
    }
}
