package com.oszero.deliver.admin.interceptor;

import com.oszero.deliver.admin.util.MdcUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.oszero.deliver.admin.constant.CommonConstant.AUTH_HEARD_NAME;
import static com.oszero.deliver.admin.constant.MdcConstant.USER_ID_NAME;

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
        String userId = getUserIdByToken(token);
        MdcUtils.put(USER_ID_NAME, userId);
        return true;
    }

    private String getUserIdByToken(String token) {
        return "oszero";
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MdcUtils.clear();
    }
}
