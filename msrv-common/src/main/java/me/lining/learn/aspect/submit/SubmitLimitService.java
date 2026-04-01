package me.lining.learn.aspect.submit;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author lining
 * @date 2026/04/01 14:20
 */
public interface SubmitLimitService {
    /**
     * 动态设置存储实现（从切面传入，由注解指定）
     * @param storageService 注解指定的存储实现实例
     */
    void setStorageService(SubmitLimitStorageService storageService);

    /**
     * 生成幂等key
     * @param joinPoint 切面连接点（获取方法信息、参数信息）
     * @param submitLimit 幂等注解信息
     * @return 唯一幂等key
     */
    String generateKey(ProceedingJoinPoint joinPoint, SubmitLimit submitLimit);

    /**
     * 判断幂等key是否存在
     * @param key 幂等key
     * @param submitLimit 幂等注解信息（获取过期时间等参数）
     * @return true：存在（重复提交），false：不存在（正常提交）
     */
    boolean exists(String key, SubmitLimit submitLimit);

    /**
     * 存储幂等key（设置过期时间）
     * @param key 幂等key
     * @param submitLimit 幂等注解信息（获取过期时间等参数）
     */
    void storeKey(String key, SubmitLimit submitLimit);

    /**
     * 删除幂等key
     * @param key 幂等key
     * @param submitLimit 幂等注解信息
     */
    void removeKey(String key, SubmitLimit submitLimit);
}
