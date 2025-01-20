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

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.admin.constant.AdminConstant;
import com.oszero.deliver.business.admin.service.GroupService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import com.oszero.deliver.web.admin.util.PathUtils;
import com.oszero.deliver.web.admin.util.ResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class GroupInterceptor implements HandlerInterceptor {

    private final Set<String> INTERCEPT_PATH =
            Set.of(
                    AdminPathConstant.MESSAGE_TEMPLATE_PREFIX_PATH,
                    AdminPathConstant.CHANNEL_APP_PREFIX_PATH,
                    AdminPathConstant.PLATFORM_FILE_PREFIX_PATH,
                    AdminPathConstant.SEND_TASK_PREFIX_PATH,
                    AdminPathConstant.PEOPLE_GROUP_PREFIX_PATH
            );
    private final GroupService groupService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessPrePath = PathUtils.getBusinessPrePath(request);
        if (!INTERCEPT_PATH.contains(businessPrePath)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        String groupIdStr = request.getHeader(AdminConstant.GROUP_ID);
        if (StrUtil.isBlank(groupIdStr) || !NumberUtil.isLong(groupIdStr)) {
            ResultUtils.response(CommonResult.fail("分组id非法"), response);
            return false;
        }
        Long groupId = Long.valueOf(groupIdStr);
        GroupUtils.setGroupId(groupId);
        boolean hasFlag = groupService.currentLoginUserHasGroup(groupId);
        if (!hasFlag) {
            ResultUtils.response(CommonResult.fail("无此分组权限"), response);
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        GroupUtils.clearGroupId();
    }
}
