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
import com.oszero.deliver.platformclient.common.ClientConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author oszero
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ChannelProviderTypeEnum {
    DEFAULT(newIndex(), ClientConstant.DEFAULT),
    ALI(newIndex(), ClientConstant.ALI_YUN),
    TENCENT(newIndex(), ClientConstant.TENCENT);

    private static int index = 0;

    private final String code;
    private final String name;

    public static final int MIN_INDEX = CommonConstant.MIN_INDEX;
    public static final int MAX_INDEX = 3;

    public static ChannelProviderTypeEnum getInstanceByCode(Integer code) {
        for (ChannelProviderTypeEnum value : values()) {
            if (StrUtil.equals(value.code, String.valueOf(code))) {
                return value;
            }
        }
        return null;
    }

    public static String newIndex() {
        return String.valueOf(++index);
    }
}
