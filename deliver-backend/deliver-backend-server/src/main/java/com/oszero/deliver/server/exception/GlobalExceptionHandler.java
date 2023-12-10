package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.common.CommonResult;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * DTO 参数校验失败的异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public CommonResult<?> handleBindingException(Exception e, HttpServletRequest request) {
        log.error("请求参数非预期异常: {} - {}, error = {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 消息链路异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(MessageException.class)
    public CommonResult<?> handleMessageException(MessageException e, HttpServletRequest request) {
        // 记录错误日志
        MessageLinkTraceUtils.recordMessageLifecycleErrorLog(e.getMessage());
        // 记录异常信息到生命周期日志中
        MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(e.getMessage());
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 系统异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(SystemException.class)
    public CommonResult<?> handleSystemException(SystemException e, HttpServletRequest request) {
        log.error("[SystemException], {}", e.getMessage());
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 异常兜底处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult<?> handleThrowable(Exception e, HttpServletRequest request) {
        log.error("[handleThrowable], {} ", e.getMessage());
        return CommonResult.fail(ResultEnum.ERROR);
    }


}
