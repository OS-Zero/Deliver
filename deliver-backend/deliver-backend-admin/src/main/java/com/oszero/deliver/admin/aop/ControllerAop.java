package com.oszero.deliver.admin.aop;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.admin.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 控制器切面
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    /**
     * 切点
     */
    @Pointcut(value = "execution(* com.oszero.deliver.admin.controller.*.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 前置切点
     *
     * @param joinPoint 连接点
     */
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

    /**
     * 后置切点
     *
     * @param joinPoint 连接点
     * @param result    返回值
     */
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

    /**
     * 抛异常切点
     *
     * @param joinPoint 连接点
     * @param ex        异常
     */
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
