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

package com.oszero.deliver.business.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.cache.AdminCacheManager;
import com.oszero.deliver.business.admin.mapper.UserInfoMapper;
import com.oszero.deliver.business.admin.model.dto.request.messagetemplate.SendMessageRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.userinfo.*;
import com.oszero.deliver.business.admin.model.dto.response.userinfo.UserInfoResponseDto;
import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;
import com.oszero.deliver.business.admin.model.entity.database.UserInfo;
import com.oszero.deliver.business.admin.service.UserInfoService;
import com.oszero.deliver.business.admin.util.SendMessageUtils;
import com.oszero.deliver.business.admin.util.UserUtils;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    private final UserInfoMapper userInfoMapper;
    private final AdminCacheManager adminCacheManager;
    private final SendMessageUtils sendMessageUtils;

    @Override
    public String login(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper.eq(UserInfo::getUserEmail, userInfoLoginRequestDto.getUserEmail());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new BusinessException("邮箱或密码错误");
        }
        String digestPassword = UserUtils.getMd5DigestPassword(userInfoLoginRequestDto.getUserPassword());
        if (!StrUtil.equals(digestPassword, userInfo.getUserPassword())) {
            throw new BusinessException("邮箱或密码错误");
        }
        // 代表登录成功
        String token = UserUtils.generateLoginToken();
        CacheUserInfo cacheUserInfo = new CacheUserInfo();
        BeanUtil.copyProperties(userInfo, cacheUserInfo);
        cacheUserInfo.setUserToken(token);
        adminCacheManager.saveLoginUser(token, cacheUserInfo);
        return token;
    }

    @Override
    public void register(UserInfoRegisterRequestDto userInfoRegisterRequestDto) {
        String userEmail = userInfoRegisterRequestDto.getUserEmail();
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper.eq(UserInfo::getUserEmail, userEmail);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (!Objects.isNull(userInfo)) {
            throw new BusinessException("当前邮箱已注册");
        }
        String verificationCode = adminCacheManager.getVerificationCodeByUserEmail(userEmail);
        if (!StrUtil.equals(verificationCode, userInfoRegisterRequestDto.getVerificationCode())) {
            throw new BusinessException("验证码错误");
        }
        userInfo = new UserInfo();
        String md5DigestPassword = UserUtils.getMd5DigestPassword(userInfoRegisterRequestDto.getUserPassword());
        userInfoRegisterRequestDto.setUserPassword(md5DigestPassword);
        BeanUtil.copyProperties(userInfoRegisterRequestDto, userInfo);
        int insert = userInfoMapper.insert(userInfo);
        if (DataBaseUtils.isSingleDataModifyFail(insert)) {
            throw new BusinessException("注册失败");
        }
    }

    @Override
    public void forgetPassword(UserInfoForgetPasswordRequestDto userInfoUpdatePasswordRequestDto) {
        String verificationCode = adminCacheManager.getVerificationCodeByUserEmail(userInfoUpdatePasswordRequestDto.getUserEmail());
        if (!StrUtil.equals(verificationCode, userInfoUpdatePasswordRequestDto.getVerificationCode())) {
            throw new BusinessException("验证码错误");
        }
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper.eq(UserInfo::getUserEmail, userInfoUpdatePasswordRequestDto.getUserEmail());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new BusinessException("邮箱错误");
        }
        userInfo.setUserPassword(UserUtils.getMd5DigestPassword(userInfoUpdatePasswordRequestDto.getUserPassword()));
        int update = userInfoMapper.updateById(userInfo);
        if (DataBaseUtils.isSingleDataModifyFail(update)) {
            throw new BusinessException("更新密码失败");
        }
    }

    @Override
    public void updatePassword(UserInfoUpdatePasswordRequestDto userInfoUpdatePasswordRequestDto) {
        String verificationCode = adminCacheManager.getVerificationCodeByUserEmail(UserUtils.getCurrentLoginUserEmail());
        if (!StrUtil.equals(verificationCode, userInfoUpdatePasswordRequestDto.getVerificationCode())) {
            throw new BusinessException("验证码错误");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(UserUtils.getCurrentLoginUserId());
        userInfo.setUserPassword(UserUtils.getMd5DigestPassword(userInfoUpdatePasswordRequestDto.getUserPassword()));
        int update = userInfoMapper.updateById(userInfo);
        if (DataBaseUtils.isSingleDataModifyFail(update)) {
            throw new BusinessException("更新密码失败");
        }
    }

    @Override
    public void getVerificationCode(UserInfoGetVerificationCodeRequestDto userInfoGetVerificationCodeRequestDto) {
        String verificationCode = UserUtils.generateVerificationCode();
        String userEmail = userInfoGetVerificationCodeRequestDto.getUserEmail();
        adminCacheManager.saveVerificationCode(userEmail, verificationCode);
        HashMap<String, Object> messageParam = new HashMap<>();
        messageParam.put("title", "Deliver验证码邮件");
        messageParam.put("content", String.format(
                "您的验证码为：%s，请在10分钟内完成验证。", verificationCode
        ));
        messageParam.put("htmlFlag", true);
        SendMessageRequestDto sendMessageRequestDto = new SendMessageRequestDto();
        sendMessageRequestDto.setTemplateId(1L);
        sendMessageRequestDto.setUsers(List.of(userEmail));
        sendMessageRequestDto.setMessageParam(messageParam);
        sendMessageUtils.sendMessage(sendMessageRequestDto);
    }

    @Override
    public UserInfoResponseDto getCurrentLoginUserInfo() {
        CacheUserInfo userInfo = UserUtils.getCurrentLoginUserInfo();
        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
        BeanUtil.copyProperties(userInfo, userInfoResponseDto);
        return userInfoResponseDto;
    }

    @Override
    public void logout() {
        adminCacheManager.deleteLoginUser(UserUtils.getCurrentLoginUserToken());
    }
}
