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

package com.oszero.deliver.business.common.enums;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelTypeEnum {
    CALL(newIndex(), "电话"),
    SMS(newIndex(), "短信"),
    MAIL(newIndex(), "邮件"),
    DING(newIndex(), "钉钉"),
    WECHAT(newIndex(), "企业微信"),
    FEI_SHU(newIndex(), "飞书"),
    OFFICIAL_ACCOUNT(newIndex(), "微信公众号");

    private static int index = 0;

    private static String newIndex() {
        return String.valueOf(++index);
    }

    private final String code;
    private final String name;

    public static final int MIN_INDEX = CommonConstant.MIN_INDEX;
    public static final int MAX_INDEX = 7;

    public static ChannelTypeEnum getInstanceByCode(Integer code) {
        for (ChannelTypeEnum typeEnum : values()) {
            if (StrUtil.equals(typeEnum.getCode(), String.valueOf(code))) {
                return typeEnum;
            }
        }
        return null;
    }
}
