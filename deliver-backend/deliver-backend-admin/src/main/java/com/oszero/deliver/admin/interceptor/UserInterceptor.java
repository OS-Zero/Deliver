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
