package me.lining.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lining
 * @date 2026/03/01 03:19
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {
    public static void main(String[] args) throws InterruptedException {
        // 适配Nacos 9848端口就绪延迟，可选（和之前user服务的等待逻辑一致）
        SpringApplication.run(CloudGatewayApplication.class, args);
    }
}
