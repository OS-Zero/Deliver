package com.oszero.deliver.admin.filter;

import cn.hutool.core.lang.UUID;
import com.oszero.deliver.admin.util.MdcUtils;
import jakarta.servlet.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.oszero.deliver.admin.constant.MdcConstant.TRANCE_ID_NAME;

/**
 * traceId 过滤器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnMissingBean(name = "traceIdInterceptor")
public class TraceIdFilter implements Filter {

    /**
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @param filterChain     过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException 异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MdcUtils.put(TRANCE_ID_NAME, UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MdcUtils.clear();
        }
    }
}
