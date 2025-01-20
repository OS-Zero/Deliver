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

package com.oszero.deliver.business.admin.cache;

import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;
import com.oszero.deliver.business.common.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AdminCacheManager {

    private final RedisUtils adminRedisUtils;

    /********** 用户相关业务 **********/
    public void saveLoginUser(String token, CacheUserInfo loginUser) {
        adminRedisUtils.saveJsonObjByKeyAndExpireTime(AdminCacheConstant.LOGIN_USER_PREFIX + token,
                loginUser, 3L, TimeUnit.DAYS);
    }

    public CacheUserInfo getLoginUser(String token) {
        return adminRedisUtils.getJsonObjByKey(AdminCacheConstant.LOGIN_USER_PREFIX + token, CacheUserInfo.class);
    }

    public void deleteLoginUser(String token) {
        adminRedisUtils.deleteByKey(AdminCacheConstant.LOGIN_USER_PREFIX + token);
    }

    public String getVerificationCodeByUserEmail(String userEmail) {
        return adminRedisUtils.getStringByKey(AdminCacheConstant.VERIFICATION_CODE_PREFIX + userEmail);
    }

    public void saveVerificationCode(String userEmail, String verificationCode) {
        adminRedisUtils.savaStringByKeyAndExpireTime(AdminCacheConstant.VERIFICATION_CODE_PREFIX + userEmail,
                verificationCode, 10L, TimeUnit.MINUTES);
    }
}
