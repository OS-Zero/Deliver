package com.oszero.deliver.server.aop;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerAop {

    @Pointcut(value = "execution(* com.oszero.deliver.server.web.controller..*.*(..))")
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
}
