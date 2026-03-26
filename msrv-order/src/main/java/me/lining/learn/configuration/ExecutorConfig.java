package me.lining.learn.configuration;

import com.alibaba.ttl.threadpool.TtlExecutors;
import me.lining.learn.trace.MDCTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lining
 * @date 2026/03/26 20:14
 */
@Configuration
public class ExecutorConfig {
    @Bean
    public Executor executor() {
        return TtlExecutors.getTtlExecutor(Executors.newFixedThreadPool(5));
    }


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //配置队列大小
        executor.setQueueCapacity(100);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("mdc-trace-");
        // 异步MDC
        executor.setTaskDecorator(new MDCTaskDecorator());
        //执行初始化
        executor.initialize();
        return executor;
    }
}