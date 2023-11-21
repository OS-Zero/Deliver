package com.oszero.deliver.admin.exception;


import com.oszero.deliver.admin.enums.ResultEnum;
import com.oszero.deliver.admin.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
     * 业务异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("[BusinessException] ", e);
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 参数校验失败的异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public CommonResult<?> handleBindingException(Exception e, HttpServletRequest request) {
        log.error("[Binding] ", e);
        log.warn("请求参数非预期异常: {} - {}, error = {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 异常兜底处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return 错误
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception e, HttpServletRequest request) {
        log.error("[handleException] ", e);
        return CommonResult.fail(ResultEnum.ERROR);
    }
}
