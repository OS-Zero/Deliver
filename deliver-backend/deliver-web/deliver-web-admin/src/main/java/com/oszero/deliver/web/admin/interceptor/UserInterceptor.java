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

package com.oszero.deliver.web.admin.interceptor;

import com.oszero.deliver.business.admin.cache.AdminCacheManager;
import com.oszero.deliver.business.admin.constant.AdminConstant;
import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;
import com.oszero.deliver.business.admin.util.UserUtils;
import com.oszero.deliver.business.common.constant.ServerPathConstant;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import com.oszero.deliver.web.admin.util.PathUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.Set;

/**
 * 用户拦截器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    private final AdminCacheManager adminCacheManager;
    private final Set<String> NO_INTERCEPT_PATH = Set.of(
        AdminPathConstant.USER_LOGIN,
        AdminPathConstant.USER_REGISTER,
        AdminPathConstant.USER_FORGET_PASSWORD,
        AdminPathConstant.USER_GET_VERIFICATION_CODE,
        ServerPathConstant.OPEN_SEND_MESSAGE
    );
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessPath = PathUtils.getBusinessPath(request);
        if (NO_INTERCEPT_PATH.contains(businessPath)) {
            return true;
        }
        String token = request.getHeader(AdminConstant.AUTH_HEARD_NAME);
        CacheUserInfo userInfo = adminCacheManager.getLoginUser(token);
        if (Objects.isNull(userInfo) ) {
            response.sendRedirect(request.getContextPath() + AdminPathConstant.USER_LOGIN);
            return false;
        }
        UserUtils.saveCurrentLoginUserInfo(userInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtils.removeCurrentLoginUserInfo();
    }
}
