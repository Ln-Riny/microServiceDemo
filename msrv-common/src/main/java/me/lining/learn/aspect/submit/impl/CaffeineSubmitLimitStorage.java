package me.lining.learn.aspect.submit.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import me.lining.learn.aspect.submit.SubmitLimitStorageService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lining
 * @date 2026/04/01 14:55
 */
@Service("caffeineIdempotentStorage")
public class CaffeineSubmitLimitStorage implements SubmitLimitStorageService {

    // 初始化Caffeine缓存，设置过期时间（默认30秒，可配置）
    private final Cache<String, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(300, TimeUnit.SECONDS)
            .maximumSize(10000) // 缓存最大数量，避免内存溢出
            .build();

    @Override
    public boolean exists(String key) {
        // 缓存中存在key，返回true（重复提交）
        return cache.getIfPresent(key) != null;
    }

    @Override
    public void store(String key, long expire) {
        // 存储key，value随意，过期时间覆盖默认值
        cache.put(key, new Object());
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }
}