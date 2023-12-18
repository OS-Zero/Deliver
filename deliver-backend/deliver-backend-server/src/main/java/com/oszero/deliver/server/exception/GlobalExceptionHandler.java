package com.oszero.deliver.server.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.common.CommonResult;
import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.util.ThreadLocalUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageRecordService messageRecordService;

    /**
     * DTO 参数校验失败的异常处理
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
        try {
            // 记录消息发送失败
            SendTaskDto sendTaskDto = ThreadLocalUtils.getSendTaskDto();
            sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(sendTaskDto, StatusEnum.OFF, user));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
