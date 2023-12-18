package com.oszero.deliver.admin.exception;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.admin.enums.ResultEnum;
import com.oszero.deliver.admin.model.common.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * 全局异常处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class AdminGlobalExceptionHandler {

    /**
     * 业务异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult<Void> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("[BusinessException, {}] ", e.getMessage());
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 参数校验失败的异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleBindingException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("请求参数非预期异常: {} - {}, error = {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        return CommonResult.fail(exceptionStr);
    }

    /**
     * 异常兜底处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return CommonResult
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult<Void> handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("[handleThrowable, {}] ", e.getMessage());
        return CommonResult.fail(ResultEnum.ERROR);
    }
}
