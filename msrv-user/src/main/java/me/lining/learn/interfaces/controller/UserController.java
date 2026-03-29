package me.lining.learn.interfaces.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lining
 * @date 2026/02/28 20:17
 */
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    // 读取Nacos中的配置
    @Value("${test.name:default}")
    private String username;

    @GetMapping("/config")
    public String getConfig() {
        return "从Nacos读取的用户名：" +username;

    }

    @GetMapping("/get/{id}")
    public String getUserById(@PathVariable Long id) {
        return "用户ID：" + id + "，用户名：张三";
    }
}