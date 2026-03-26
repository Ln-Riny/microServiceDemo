package me.lining.learn.trace;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lining
 * @date 2026/03/25 13:34
 */
public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从请求头获取 traceId（微服务传递）
        String traceId = request.getHeader("traceId");

        // 2. 没有则生成
        if (traceId == null || traceId.isEmpty()) {
            traceId = TraceContext.generateTraceId();
        }

        // 3. 存入上下文
        TraceContext.setTraceId(traceId);
        return true;
    }

    // 请求完成后必须清除
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TraceContext.remove();
    }
}