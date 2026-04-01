package me.lining.learn.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lining
 * @date 2026/04/01 14:27
 */
@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    REPEAT_SUBMIT(400, "重复提交"),;

    private int code;

    private String message;
}
