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

package com.oszero.deliver.business.common.aop;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.business.common.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    @Pointcut(value = "execution(* com.oszero.deliver.web.*.controller.*.*(..))")
    public void controllerPointcut() {
    }

    @Before(value = "controllerPointcut()")
    public void before(JoinPoint joinPoint) {
        // 获取 IP 地址
        String ip = IpUtils.getClientIp();
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        String params = JSONUtil.toJsonStr(args);
        // 打印日志
        log.info("IP地址为: {}，请求进入 [{}#{}] 请求参数为: {}", ip, className, methodName, params);
    }

    @AfterReturning(value = "controllerPointcut()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        // 获取 IP 地址
        String ip = IpUtils.getClientIp();
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 获取响应参数
        String resultJson = JSONUtil.toJsonStr(result);
        // 打印日志
        log.info("IP地址为: {}，请求返回 [{}#{}] 响应参数为: {}", ip, className, methodName, resultJson);
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        // 获取 IP 地址
        String ip = IpUtils.getClientIp();
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 打印异常日志
        log.error("IP地址为: {}，请求 [{}#{}] 发生异常: {}", ip, className, methodName, ex.getMessage());
    }
}
