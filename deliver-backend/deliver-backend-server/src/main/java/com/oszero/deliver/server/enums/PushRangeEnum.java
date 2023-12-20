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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 推送范围枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum PushRangeEnum {
    /**
     * 不限
     */
    ALL(0, "不限"),
    /**
     * 企业内部
     */
    INNER(1, "企业内部"),

    /**
     * 外部用户
     */
    EXTERNAL(2, "外部用户");

    private final Integer code;
    private final String name;

    public static PushRangeEnum getInstanceByCode(Integer code) {
        for (PushRangeEnum item : values()
        ) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
