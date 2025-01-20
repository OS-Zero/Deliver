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

package com.oszero.deliver.business.admin.model.dto.request.userinfo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
public class UserInfoUpdatePasswordRequestDto {

    @Length(min = 6, max = 16, message = "密码长度必须在6到16个字符之间")
    private String userPassword;

    @Length(min = 6, max = 6, message = "验证码长度必须为6位")
    private String verificationCode;
}
