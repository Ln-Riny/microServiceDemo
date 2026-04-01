package me.lining.learn.aspect.submit;

/**
 * @author lining
 * @date 2026/04/01 13:20
 */
public interface SubmitLimitStorageService {
    /**
     * 判断key是否存在
     * @param key 幂等key
     * @return true：存在，false：不存在
     */
    boolean exists(String key);

    /**
     * 存储key，设置过期时间
     * @param key 幂等key
     * @param expire 过期时间（秒）
     */
    void store(String key, long expire);

    /**
     * 删除key
     * @param key 幂等key
     */
    void remove(String key);
}
