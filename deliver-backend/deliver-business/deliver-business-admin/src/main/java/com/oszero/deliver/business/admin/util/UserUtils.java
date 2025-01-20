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

package com.oszero.deliver.business.admin.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.oszero.deliver.business.admin.constant.AdminConstant;
import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;

import java.util.Random;

/**
 * @author oszero
 * @version 1.0.0
 */
public class UserUtils {

    public static String getMd5DigestPassword(String password) {
        if (StrUtil.isBlank(password)) {
            return null;
        }
        return MD5.create().digestHex(password);
    }

    public static String generateLoginToken() {
        return AdminConstant.AUTH_TOKEN_PREFIX + UUID.randomUUID();
    }

    public static String generateVerificationCode() {
        // 定义验证码的长度
        int length = 6;
        // 创建一个字符集，包括数字0-9
        String digits = "0123456789";
        // 创建一个随机数生成器
        Random random = new Random();
        StringBuilder code = new StringBuilder(length);
        // 随机从字符集中选择6个字符组成验证码
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            code.append(digits.charAt(index));
        }
        return code.toString();
    }

    public static boolean isAdminUser() {
        return StrUtil.equals(getCurrentLoginUserInfo().getUserRole(), "admin");
    }

    public static long getCurrentLoginUserId() {
        return getCurrentLoginUserInfo().getUserId();
    }

    public static String getCurrentLoginUserToken() {
        return getCurrentLoginUserInfo().getUserToken();
    }

    public static String getCurrentLoginUserEmail() {
        return getCurrentLoginUserInfo().getUserEmail();
    }

    public static CacheUserInfo getCurrentLoginUserInfo() {
        return ThreadLocalUtils.getUserInfo();
    }

    public static void saveCurrentLoginUserInfo(CacheUserInfo userInfo) {
        ThreadLocalUtils.setUserInfo(userInfo);
    }

    public static void removeCurrentLoginUserInfo() {
        ThreadLocalUtils.clearUserInfo();
    }
}
