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

package com.oszero.deliver.server.enums;

import lombok.Getter;

/**
 * 响应状态枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public enum ResultEnum {
    SUCCESS(200, "success"),
    CLIENT_ERROR(400, "client_error"),
    SERVER_ERROR(500, "server_error"),
    ERROR(600, "error"),
    REFRESH_TOKEN(700, "refresh_token");
    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}