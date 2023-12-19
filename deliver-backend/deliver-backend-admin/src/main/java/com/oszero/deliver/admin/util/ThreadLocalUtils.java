package com.oszero.deliver.admin.util;

import com.oszero.deliver.admin.model.entity.UserInfo;

/**
 * ThreadLocal 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class ThreadLocalUtils {
    private final static ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo) {
        USER_INFO_THREAD_LOCAL.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_INFO_THREAD_LOCAL.remove();
    }
}
