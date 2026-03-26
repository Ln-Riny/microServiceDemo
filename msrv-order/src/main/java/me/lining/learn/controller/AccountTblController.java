package me.lining.learn.controller;


import me.lining.learn.entity.AccountTbl;
import me.lining.learn.service.AccountTblService;
import me.lining.learn.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author your_name
 * @since 2026-03-21
 */
@RestController
@RequestMapping("/accountTbl")
public class AccountTblController {

    @Autowired
    private AccountTblService accountTblService;

    @RequestMapping("/insert")
    public Boolean insert() {
        return accountTblService.save(AccountTbl.builder().userId("2L").money(1000).build());
    }

    @RequestMapping("/test")
    public void test() {
        System.out.println("主线程：" + TraceContext.getTraceId());

        // 开启子线程
        new Thread(() -> {
            // 子线程 自动 获取父线程的 traceId！
            System.out.println("子线程：" + TraceContext.getTraceId());
        }).start();
    }
}

