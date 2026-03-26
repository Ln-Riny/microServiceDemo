package me.lining.learn.trace;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author lining
 * @date 2026/03/26 19:56
 */
public class TraceIdUtils {

    public static final String TRACE_ID = "traceId";

    // 生成 traceId
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // 存入 MDC
    public static void setTraceId(String traceId) {

        MDC.put(TRACE_ID, traceId);
    }

    // 获取 traceId
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    // 清除
    public static void removeTraceId() {
        MDC.remove(TRACE_ID);
    }
}
