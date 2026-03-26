package me.lining.learn.configuration;

import me.lining.learn.trace.TraceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lining
 * @date 2026/03/25 13:42
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 TraceInterceptor
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**"); // 拦截所有请求
    }
}