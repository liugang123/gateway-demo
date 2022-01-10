package org.example.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liugang
 * @create 2021/12/24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo(scanBasePackages = "org.example.dubbo.provider")
public class DubboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication.class, args);
    }
}
