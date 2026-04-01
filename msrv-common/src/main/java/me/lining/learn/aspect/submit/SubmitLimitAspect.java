package me.lining.learn.aspect.submit;

import lombok.extern.slf4j.Slf4j;
import me.lining.learn.result.SubmitLimitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author lining
 * @date 2026/04/01 14:19
 */
@Aspect
@Component
@Slf4j
public class SubmitLimitAspect {
    @Autowired
    private Map<String, SubmitLimitStorageService> storageServiceMap;

    // 注入核心逻辑服务
    @Autowired
    private SubmitLimitService submitLimitService;

    // 切入点：拦截所有加了@Idempotent注解的类或方法
    @Pointcut("@within(me.lining.learn.aspect.submit.SubmitLimit) || @annotation(me.lining.learn.aspect.submit.SubmitLimit)")
    public void idempotentPointCut() {}

    // 环绕通知，前置判断幂等，后置清理key
    @Around("idempotentPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取注解信息（类上的注解优先，方法上的注解覆盖类上的）
        SubmitLimit submitLimit = getAnnotation(joinPoint);
        if (submitLimit == null) {
            // 无注解，直接执行业务方法
            return joinPoint.proceed();
        }

        // 2. 从注解中获取指定的存储实现Bean名称，动态获取存储实例（核心优化点）
        String storageImplBeanName = submitLimit.storageImpl();
        SubmitLimitStorageService storageService = storageServiceMap.get(storageImplBeanName);
        if (storageService == null) {
            throw new SubmitLimitException("指定的存储实现不存在，请检查storageImpl参数：" + storageImplBeanName);
        }
        // 将动态获取的存储实例设置到核心服务，供后续使用
        submitLimitService.setStorageService(storageService);

        // 3. 生成幂等key
        String idempotentKey = submitLimitService.generateKey(joinPoint, submitLimit);
        log.info("幂等key生成：{}", idempotentKey);

        // 4. 幂等判断：判断key是否存在（使用注解指定的存储实现）
        boolean exists = submitLimitService.exists(idempotentKey, submitLimit);
        if (exists) {
            // 存在，抛出重复提交异常
            throw new SubmitLimitException(submitLimit.message());
        }

        // 5. 存储幂等key（设置过期时间，使用指定的存储实现）
        submitLimitService.storeKey(idempotentKey, submitLimit);

        try {
            // 6. 执行业务方法
            Object result = joinPoint.proceed();
            // 7. 业务执行成功，删除幂等key（可选，根据业务场景：如表单提交成功后删除，避免后续无法提交）
            // 若业务需要“一次提交，永久幂等”（如支付回调），可注释此行
//            submitLimitService.removeKey(idempotentKey, submitLimit);
            return result;
        } catch (Exception e) {
            // 8. 业务执行失败，删除幂等key（避免死锁，允许重试）
            submitLimitService.removeKey(idempotentKey, submitLimit);
            log.error("业务执行失败，清理幂等key：{}", idempotentKey, e);
            throw e;
        }
    }

    // 辅助方法：获取方法/类上的@Idempotent注解（不变）
    private SubmitLimit getAnnotation(ProceedingJoinPoint joinPoint) {
        // 先获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SubmitLimit methodAnnotation = method.getAnnotation(SubmitLimit.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        // 方法上没有，获取类上的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return targetClass.getAnnotation(SubmitLimit.class);
    }
}
