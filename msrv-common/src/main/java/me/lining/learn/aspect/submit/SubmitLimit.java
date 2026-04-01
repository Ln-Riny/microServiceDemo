package me.lining.learn.aspect.submit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lining
 * @date 2026/04/01 13:14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SubmitLimit {
    /**
     * 幂等key前缀（区分不同业务场景，避免key冲突）
     * 默认为空，自动拼接 业务模块名 + 方法名
     */
    String prefix() default "";

    /**
     * 幂等key的生成方式（枚举）
     * DEFAULT：默认（请求参数+用户ID/Token）
     * CUSTOM：自定义（通过@IdempotentKey指定参数）
     * MQ：MQ消费场景（使用消息ID作为幂等key）
     */
    SubmitType type() default SubmitType.DEFAULT;

    /**
     * 过期时间（单位：秒）
     * 默认30秒，根据业务场景调整（如支付接口可设60秒，表单提交设10秒）
     */
    long expire() default 30;

    /**
     * 重复提交时的提示信息
     */
    String message() default "操作过于频繁，请稍后再试";

    /**
     * 显式指定存储实现的Bean名称（必填，适配common模块）
     * 对应存储层实现类的@Service注解Bean名称，如：redisIdempotentStorage、caffeineIdempotentStorage
     * 开发者可在业务模块自定义存储实现，通过该参数指定使用
     */
    String storageImpl() default "caffeineIdempotentStorage"; // 默认使用Redis存储实现
}
