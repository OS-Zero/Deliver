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

package com.oszero.deliver.business.admin.validator;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.admin.annotation.PlatformFileTypeRange;
import com.oszero.deliver.business.common.enums.PlatformFileTypeEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
public class PlatformFileTypeRangeValidator implements ConstraintValidator<PlatformFileTypeRange, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StrUtil.isNotBlank(value) && !Objects.isNull(PlatformFileTypeEnum.getInstanceByCode(value));
    }
}

