package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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
     * 责任链流水线的异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(LinkProcessException.class)
    public CommonResult<?> handlePipelineProcessException(LinkProcessException e, HttpServletRequest request) {
        log.error("[LinkProcessException] ", e);
        return CommonResult.fail(e.getMessage());
    }

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
     * 系统异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(SystemException.class)
    public CommonResult<?> handleSystemException(SystemException e, HttpServletRequest request) {
        log.error("[SystemException] ", e);
        return CommonResult.fail(e.getMessage());
    }

    /**
     * 异常兜底处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception e, HttpServletRequest request) {
        log.error("[handleException] ", e);
        return CommonResult.fail(ResultEnum.ERROR);
    }


}
