package me.lining.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lining
 * @date 2026/02/28 20:09
 */
@SpringBootApplication
// 开启服务注册发现
@EnableDiscoveryClient
public class MsrvUserApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MsrvUserApplication.class, args);
    }

}
