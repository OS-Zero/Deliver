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

package com.oszero.deliver.business.common.model.common;

import com.oszero.deliver.business.common.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private T data;
    private String errorMessage;

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), null, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), data, null);
    }

    public static <T> CommonResult<T> fail(ResultEnum resultEnum) {
        return new CommonResult<>(resultEnum.getCode(), null, resultEnum.getMessage());
    }

    public static <T> CommonResult<T> fail(String errorMessage) {
        return new CommonResult<>(ResultEnum.FAIL.getCode(), null, errorMessage);
    }
}
