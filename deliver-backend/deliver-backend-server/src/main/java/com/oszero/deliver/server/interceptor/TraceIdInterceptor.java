package com.oszero.deliver.server.interceptor;

import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.log.trace.TraceIdStrategy;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.util.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * traceId 拦截器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class TraceIdInterceptor implements HandlerInterceptor {

    private final TraceIdStrategy traceIdStrategy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TraceIdConstant.TRACE_ID);
        if (!Objects.isNull(traceId)) {
            //  此处是为了适配网关
            MDCUtils.put(TraceIdConstant.TRACE_ID, traceId);
        } else {
            String traceID = traceIdStrategy.createTraceId();
            MDCUtils.put(TraceIdConstant.TRACE_ID, traceID);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDCUtils.clear();
        ThreadLocalUtils.clear();
    }
}
