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

package com.oszero.deliver.web.server.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.enums.ResultEnum;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.platformclient.exception.ClientException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RestControllerAdvice(basePackages = "com.oszero.deliver.web.server")
@RequiredArgsConstructor
public class ServerGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleBindingException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        return CommonResult.fail(exceptionStr);
    }

    @ExceptionHandler(MessageException.class)
    public CommonResult<Void> handleMessageException(MessageException e, HttpServletRequest request) {
        return CommonResult.fail(e.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    public CommonResult<Void> handleSystemException(ClientException e, HttpServletRequest request) {
        return CommonResult.fail(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public CommonResult<Void> handleThrowable(Exception e, HttpServletRequest request) {
        return CommonResult.fail(ResultEnum.ERROR);
    }
}
