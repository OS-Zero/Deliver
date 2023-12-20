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

package com.oszero.deliver.admin.interceptor;

import com.oszero.deliver.admin.model.entity.UserInfo;
import com.oszero.deliver.admin.util.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.oszero.deliver.admin.constant.CommonConstant.AUTH_HEARD_NAME;

/**
 * 用户拦截器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader(AUTH_HEARD_NAME);
        // 转换
        UserInfo userInfo = getUserInfoByToken(token);
        ThreadLocalUtils.setUserInfo(userInfo);
        return true;
    }

    private UserInfo getUserInfoByToken(String token) {
        return UserInfo.builder()
                .userId("oszero-123456")
                .username("oszero.cn(零号开源)")
                .realName("oszero大家庭").build();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.clear();
    }
}
