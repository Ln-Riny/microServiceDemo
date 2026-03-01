package me.lining.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lining
 * @date 2026/02/28 20:09
 */
@SpringBootApplication
// 开启服务注册发现
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "me.lining.learn.services")
public class MsrvOrderApplication {
    public static void main(String[] args)  {
        SpringApplication.run(MsrvOrderApplication.class, args);
    }

}
