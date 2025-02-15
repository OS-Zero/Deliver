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

package com.oszero.deliver.business.server.util;

import com.lmax.disruptor.RingBuffer;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;

/**
 * @author oszero
 * @version 1.0.0
 */
public class DisruptorUtils {

    public static void publishEvent(SendTaskDto sendTaskDto, RingBuffer<? extends DisruptorBaseEvent> ringBuffer) {
        long sequence = ringBuffer.next();  // 获取下一个事件的序列号
        try {
            DisruptorBaseEvent event = ringBuffer.get(sequence);  // 获取该位置的事件对象
            event.setSendTaskDto(sendTaskDto);  // 设置事件数据
        } finally {
            ringBuffer.publish(sequence);  // 发布事件
        }
    }
}
