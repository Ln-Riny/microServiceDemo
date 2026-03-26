package me.lining.learn.trace;

/**
 * @author lining
 * @date 2026/03/25 13:34
 */
public class TraceContext {
    // 关键：使用 InheritableThreadLocal → 子线程自动继承父线程的值
    private static final InheritableThreadLocal<String> TRACE_ID = new InheritableThreadLocal<>();

    // 设置 traceId
    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    // 获取 traceId
    public static String getTraceId() {
        return TRACE_ID.get();
    }

    // 必须 remove！防止内存泄漏
    public static void remove() {
        TRACE_ID.remove();
    }

    // 生成 traceId
    public static String generateTraceId() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
