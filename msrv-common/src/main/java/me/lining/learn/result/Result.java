package me.lining.learn.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author lining
 * @date 2026/04/01 14:26
 */
@Builder
@Data
public class Result <T> {

    private int code;

    private String message;

    private T data;

    private long timestamp;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> Result<T> fail(ResultCode resultCode, String message) {
        return Result.<T>builder()
                .code(resultCode.getCode())
                .message(message)
                .build();
    }
}
