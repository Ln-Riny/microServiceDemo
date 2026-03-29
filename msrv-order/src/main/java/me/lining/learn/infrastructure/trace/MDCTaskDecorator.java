package me.lining.learn.infrastructure.trace;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * @author lining
 * @date 2026/03/26 22:19
 */
public class MDCTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        // 此时获取的是父线程的上下文数据
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (contextMap != null) {
                    // 内部为子线程的领域范围，所以将父线程的上下文保存到子线程上下文中，而且每次submit/execute调用都会更新为最新的上                     // 下文对象
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                // 清除子线程的，避免内存溢出，就和ThreadLocal.remove()一个原因
                MDC.clear();
            }
        };
    }
}