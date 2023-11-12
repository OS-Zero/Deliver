package com.oszero.deliver.server.interceptor;

import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.trace.TraceStrategy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * traceID拦截器
 *
 * @author oszero
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class TraceInterceptor implements HandlerInterceptor {

    private final TraceStrategy traceStrategy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TraceIdConstant.TRACE_ID);
        if (traceId != null) {
            //  此处是为了适配网关
            MDC.put(TraceIdConstant.TRACE_ID,traceId);
        }else {
            String traceID = traceStrategy.createTrace();
            MDC.put(TraceIdConstant.TRACE_ID,traceID);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
