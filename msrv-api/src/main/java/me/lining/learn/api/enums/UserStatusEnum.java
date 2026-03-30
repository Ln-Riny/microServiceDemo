package me.lining.learn.api.enums;

/**
 * @author lining
 * @date 2026/03/30 22:51
 */
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    LOCKED(2, "锁定"),
    DELETED(3, "删除");

    private final Integer code;
    private final String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}