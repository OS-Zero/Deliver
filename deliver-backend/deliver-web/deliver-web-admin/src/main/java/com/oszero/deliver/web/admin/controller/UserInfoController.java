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

package com.oszero.deliver.web.admin.controller;

import com.oszero.deliver.business.admin.model.dto.request.userinfo.*;
import com.oszero.deliver.business.admin.model.dto.response.userinfo.UserInfoResponseDto;
import com.oszero.deliver.business.admin.service.UserInfoService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @PostMapping(AdminPathConstant.USER_LOGIN)
    public CommonResult<String> login(@Valid @RequestBody UserInfoLoginRequestDto userInfoLoginRequestDto) {
        String token = userInfoService.login(userInfoLoginRequestDto);
        return CommonResult.success(token);
    }

    @PostMapping(AdminPathConstant.USER_REGISTER)
    public CommonResult<Void> register(@Valid @RequestBody UserInfoRegisterRequestDto userInfoRegisterRequestDto) {
        userInfoService.register(userInfoRegisterRequestDto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.USER_FORGET_PASSWORD)
    public CommonResult<Void> forgetPassword(@Valid @RequestBody UserInfoForgetPasswordRequestDto userInfoUpdatePasswordRequestDto) {
        userInfoService.forgetPassword(userInfoUpdatePasswordRequestDto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.USER_GET_VERIFICATION_CODE)
    public CommonResult<Void> getVerificationCode(@Valid @RequestBody UserInfoGetVerificationCodeRequestDto userInfoGetVerificationCodeRequestDto) {
        userInfoService.getVerificationCode(userInfoGetVerificationCodeRequestDto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.USER_UPDATE_PASSWORD)
    public CommonResult<Void> updatePassword(@Valid @RequestBody UserInfoUpdatePasswordRequestDto userInfoUpdatePasswordRequestDto) {
        userInfoService.updatePassword(userInfoUpdatePasswordRequestDto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.USER_GET_CURRENT_LOGIN_USER_INFO)
    public CommonResult<UserInfoResponseDto> getCurrentLoginUserInfo() {
        return CommonResult.success(userInfoService.getCurrentLoginUserInfo());
    }

    @PostMapping(AdminPathConstant.USER_LOGOUT)
    public CommonResult<Void> logout() {
        userInfoService.logout();
        return CommonResult.success();
    }
}
