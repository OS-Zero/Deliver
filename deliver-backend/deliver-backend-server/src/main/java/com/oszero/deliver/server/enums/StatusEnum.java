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
 * 状态枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public enum StatusEnum {
    ON(1, "启用"),
    OFF(0, "禁用");

    private final Integer status;
    private final String message;

    StatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

}
