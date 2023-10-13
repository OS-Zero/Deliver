package com.oszero.deliver.server.exception;

import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.model.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 系统异常时的错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(SystemException.class)
    public CommonResp<?> handleWeChatMessageException(SystemException e, HttpServletRequest request) {
        log.error("[SystemException] ", e);

        return CommonResp.fail(ResultEnum.ERROR);
    }

    /**
     * Http请求ContentType错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResp<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.error("[HttpMediaTypeNotSupported] ", e);
        return CommonResp.fail(ResultEnum.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR);
    }

    /**
     * Http请求MethodType错误处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResp<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("[RequestMethodNotSupported] ", e);
        return CommonResp.fail(ResultEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR);
    }

    /**
     * HttpMessage转换异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResp<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("[HttpMessageNotReadable] ", e);
        return CommonResp.fail(ResultEnum.ERROR);
    }

    /**
     * 缺少Get参数时异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResp<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.error("[MissingServletRequestParameter] ", e);
        return CommonResp.fail(ResultEnum.MISSING_SERVLET_REQUEST_PARAMETER_ERROR);
    }

    /**
     * 缺少Get参数时异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResp<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("[MethodArgumentTypeMismatch] ", e);
        return CommonResp.fail(ResultEnum.METHOD_ARGUMENT_TYPE_MISMATCH_ERROR);
    }

    /**
     * 参数校验失败的异常处理
     *
     * @param e       异常信息
     * @param request 请求
     * @return ResultVO
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public CommonResp<?> handleBindingException(Exception e, HttpServletRequest request) {
        log.error("[Binding] ", e);
        log.warn("请求参数非预期异常: {} - {}, error = {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return CommonResp.fail(ResultEnum.PARAM_VALIDATED_UN_PASS);
    }

    @ExceptionHandler(PipelineProcessException.class)
    public CommonResp<?> handlePipelineProcessException(PipelineProcessException e, HttpServletRequest request) {
        log.error("[PipelineProcessException] ", e);
        return CommonResp.fail(ResultEnum.ERROR);
    }


}
