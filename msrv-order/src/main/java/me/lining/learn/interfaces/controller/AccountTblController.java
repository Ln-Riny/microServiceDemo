package me.lining.learn.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import me.lining.learn.domain.entity.AccountTbl;
import me.lining.learn.domain.service.AccountTblService;
import me.lining.learn.infrastructure.trace.TraceIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author your_name
 * @since 2026-03-21
 */
@RestController
@Slf4j
@RequestMapping("/accountTbl")
public class AccountTblController {

    @Autowired
    private AccountTblService accountTblService;

    @Qualifier("taskExecutor")
    @Autowired
    Executor taskExecutor;

    @RequestMapping("/insert")
    public Boolean insert() {
        return accountTblService.save(AccountTbl.builder().userId("2L").money(1000).build());
    }

    @RequestMapping("/test")
    public void test() {
        log.info(("主线程：" + TraceIdUtils.getTraceId()));

        System.out.println("hellll");
        // 开启子线程
        taskExecutor.execute(() -> {
            // 子线程 自动 获取父线程的 traceId！
            log.info("子线程：" + TraceIdUtils.getTraceId());
        });
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("task B");}, taskExecutor);

        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("task C");
            }, taskExecutor);

        CompletableFuture<Void> all = f2.thenCombine(f3, (v1, v2) -> null);

        CompletableFuture<Void> res = all.thenCompose(v -> CompletableFuture.runAsync(() -> log.info("task A"), taskExecutor));
        res.join();
    }
}

