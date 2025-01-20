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

import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;

/**
 * @author oszero
 * @version 1.0.0
 */
public class ThreadLocalUtils {
    /*************************** 用户信息 *************************/
    private final static ThreadLocal<CacheUserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserInfo(CacheUserInfo userInfo) {
        USER_INFO_THREAD_LOCAL.set(userInfo);
    }
    public static CacheUserInfo getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }
    public static void clearUserInfo() {
        USER_INFO_THREAD_LOCAL.remove();
    }

    /************************** 前端平台 *************************/
    private final static ThreadLocal<String> FRONT_PLATFORM_THREAD_LOCAL = new ThreadLocal<>();
    public static void setFrontPlatform(String frontPlatform) {
    	FRONT_PLATFORM_THREAD_LOCAL.set(frontPlatform);
    }

     public static String getFrontPlatform() {
         return FRONT_PLATFORM_THREAD_LOCAL.get();
     }
     public static void clearFrontPlatform() {
         FRONT_PLATFORM_THREAD_LOCAL.remove();
     }

     /************************** 分组 *************************/
     private final static ThreadLocal<Long> GROUP_ID_THREAD_LOCAL = new ThreadLocal<>();

     public static void setGroupId(Long groupId) {
    	 GROUP_ID_THREAD_LOCAL.set(groupId);
     }

     public static Long getGroupId() {
         return GROUP_ID_THREAD_LOCAL.get();
     }

     public static void clearGroupId() {
         GROUP_ID_THREAD_LOCAL.remove();
     }
}
