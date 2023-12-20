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

package com.oszero.deliver.server.client.call;

import com.oszero.deliver.server.model.app.call.CallApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 电话客户端接口
 *
 * @author oszero
 * @version 1.0.0
 */
public interface CallClient {

    /**
     * 发送电话消息
     *
     * @param callApp     电话应用
     * @param sendTaskDto SendTaskDto
     * @throws Exception 异常
     */
    void sendCall(CallApp callApp, SendTaskDto sendTaskDto) throws Exception;

}
