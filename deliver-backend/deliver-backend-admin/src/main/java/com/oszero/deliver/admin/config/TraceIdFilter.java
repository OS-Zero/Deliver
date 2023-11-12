package com.oszero.deliver.admin.config;

import cn.hutool.core.lang.UUID;
import com.oszero.deliver.admin.util.MDCUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * traceId 过滤器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDCUtils.set("traceId", UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDCUtils.clear();
        }
    }
}
