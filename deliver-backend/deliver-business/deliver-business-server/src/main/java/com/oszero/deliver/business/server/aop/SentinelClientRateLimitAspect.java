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

package com.oszero.deliver.business.server.aop;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.oszero.deliver.business.server.annotation.ClientRateLimit;
import com.oszero.deliver.business.server.exception.MessageException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.2
 */
@Aspect
@Component
public class SentinelClientRateLimitAspect {

    @Value("${enable-limit}")
    private boolean enableLimit;

    @Around("@annotation(clientRateLimit)")
    public Object limit(ProceedingJoinPoint joinPoint, ClientRateLimit clientRateLimit) throws Throwable {
        if (!enableLimit) {
            return joinPoint.proceed();
        }
        String resourceName = clientRateLimit.resourceName();
        try (Entry entry = SphU.entry(resourceName)) {
            return joinPoint.proceed(); // 执行被注解标记的方法
        } catch (BlockException ex) {
            throw new MessageException("消息被限流降级：" + ex.getMessage());
        }
    }
}
