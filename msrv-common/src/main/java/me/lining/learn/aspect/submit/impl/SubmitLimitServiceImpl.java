package me.lining.learn.aspect.submit.impl;

import me.lining.learn.aspect.submit.SubmitLimit;
import me.lining.learn.aspect.submit.SubmitLimitService;
import me.lining.learn.aspect.submit.SubmitLimitStorageService;
import me.lining.learn.aspect.submit.SubmitType;
import me.lining.learn.result.SubmitLimitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author lining
 * @date 2026/04/01 14:50
 */
@Component
public class SubmitLimitServiceImpl implements SubmitLimitService {
    // 存储实现实例（由切面动态设置，来自注解指定的存储实现）
    private SubmitLimitStorageService storageService;

    // 注入Spring上下文，获取Bean信息（可选）
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void setStorageService(SubmitLimitStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public String generateKey(ProceedingJoinPoint joinPoint, SubmitLimit submitLimit) {
        SubmitType type = submitLimit.type();
        String prefix = getPrefix(joinPoint, submitLimit);

        // 不同类型，生成不同的key
        switch (type) {
            default:
                return prefix + ":" + getUserId() + ":";
        }
    }

    @Override
    public boolean exists(String key, SubmitLimit submitLimit) {
        if (storageService == null) {
            throw new SubmitLimitException("存储实现未初始化，请检查注解storageImpl参数");
        }
        return storageService.exists(key);
    }

    @Override
    public void storeKey(String key, SubmitLimit submitLimit) {
        if (storageService == null) {
            throw new SubmitLimitException("存储实现未初始化，请检查注解storageImpl参数");
        }
        storageService.store(key, submitLimit.expire());

    }

    @Override
    public void removeKey(String key, SubmitLimit submitLimit) {
        if (storageService == null) {
            throw new SubmitLimitException("存储实现未初始化，请检查注解storageImpl参数");
        }
        storageService.remove(key);
    }

    // ------------------------------ 辅助方法（核心，保证key唯一性，不变）------------------------------
    /**
     * 获取幂等key前缀（优先使用注解指定的prefix，否则用 类名+方法名）
     */
    private String getPrefix(ProceedingJoinPoint joinPoint, SubmitLimit submitLimit) {
        if (StringUtils.hasText(submitLimit.prefix())) {
            return submitLimit.prefix();
        }
        // 类名（简化，如OrderController → order）
        String className = joinPoint.getTarget().getClass().getSimpleName();
        className = className.replace("Controller", "").toLowerCase();
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        return className + ":" + methodName;
    }


    /**
     * 获取用户标识（适配Spring Security/Shiro，根据自己项目修改）
     * 无用户标识（如匿名接口），用IP地址替代
     */
    private String getUserId() {
        return "";
    }


}
