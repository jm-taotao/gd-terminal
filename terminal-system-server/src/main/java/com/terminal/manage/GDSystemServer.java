package com.terminal.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Jyt
 * @date 2021/9/24
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.terminal.manage.mapper"})
@EnableDiscoveryClient
@EnableFeignClients
public class GDSystemServer {
    public static void main(String[] args) {
        SpringApplication.run(GDSystemServer.class,args);
    }
}
