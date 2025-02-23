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

package com.oszero.deliver.business.server.mq.consumer.nomq;

import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.impl.FeiShuHandler;
import com.oszero.deliver.business.server.handler.impl.OfficialAccountHandler;
import com.oszero.deliver.business.server.model.event.spring.FeiShuEventSpring;
import com.oszero.deliver.business.server.model.event.spring.OfficialAccountEventSpring;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_NONE)
public class OfficialAccountEventListener implements ApplicationListener<OfficialAccountEventSpring> {

    private final NoMQCommonListener noMQCommonListener;
    private final OfficialAccountHandler officialAccountHandler;

    @Async("officialAccountAsyncExecutor")
    @Override
    public void onApplicationEvent(OfficialAccountEventSpring event) {
        noMQCommonListener.omMessageAck(event, officialAccountHandler);
    }
}
