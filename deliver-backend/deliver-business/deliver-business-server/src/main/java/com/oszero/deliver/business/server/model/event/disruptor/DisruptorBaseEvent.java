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

package com.oszero.deliver.business.server.model.event.disruptor;

import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author oszero
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class DisruptorBaseEvent {
    private SendTaskDto sendTaskDto;

    public static class CallEventDisruptor extends DisruptorBaseEvent {}
    public static class SmsEventDisruptor extends DisruptorBaseEvent {}
    public static class MailEventDisruptor extends DisruptorBaseEvent {}
    public static class DingEventDisruptor extends DisruptorBaseEvent {}
    public static class WeChatEventDisruptor extends DisruptorBaseEvent {}
    public static class FeiShuEventDisruptor extends DisruptorBaseEvent {}
    public static class OfficialAccountEventDisruptor extends DisruptorBaseEvent {}
}
