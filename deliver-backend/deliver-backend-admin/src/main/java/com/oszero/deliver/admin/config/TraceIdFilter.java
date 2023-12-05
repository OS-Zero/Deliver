package com.oszero.deliver.admin.config;

import cn.hutool.core.lang.UUID;
import com.oszero.deliver.admin.util.MdcUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * traceId 过滤器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
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
            MdcUtils.set("traceId", UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MdcUtils.clear();
        }
    }
}
