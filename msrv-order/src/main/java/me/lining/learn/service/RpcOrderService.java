package me.lining.learn.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lining
 * @date 2026/02/28 23:58
 */
@FeignClient(name = "msrv-user") // 调用的服务名
public interface RpcOrderService {

    @GetMapping("/user/get/{id}")
    String getUserById(@PathVariable("id") Long id);
}
