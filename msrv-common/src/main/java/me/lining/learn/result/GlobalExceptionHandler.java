package me.lining.learn.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lining
 * @date 2026/04/01 14:24
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获幂等异常，返回统一响应格式
    @ExceptionHandler(SubmitLimitException.class)
    public Result<Void> handleIdempotentException(SubmitLimitException e) {
        log.error("重复提交异常：{}", e.getMessage());
        // 适配项目的统一响应格式（Result是自己项目的响应类）
        return Result.fail(ResultCode.REPEAT_SUBMIT, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.fail(ResultCode.FAILED, e.getMessage());
    }

    /**
     * 所有其他异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("请求【{}】发生系统异常：", request.getRequestURI(), e);
        return Result.fail(ResultCode.FAILED, "服务器繁忙，请稍后再试...");
    }
}
