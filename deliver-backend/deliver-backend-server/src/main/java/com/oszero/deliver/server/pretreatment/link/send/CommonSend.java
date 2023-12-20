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

package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;

/**
 * 抽象类 send
 *
 * @author oszero
 * @version 1.0.0
 */
public abstract class CommonSend {

    /**
     * 模板方法，发送到消息队列
     *
     * @param sendTaskDto 发送任务
     */
    public void sendToMq(SendTaskDto sendTaskDto) {
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "开始准备发送消息到消息队列");
        send(sendTaskDto);
    }

    /**
     * 抽象方法，发送
     *
     * @param sendTaskDto 发送任务
     */
    abstract void send(SendTaskDto sendTaskDto);

}
