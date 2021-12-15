package com.terminal.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Jyt
 * @date 2021/9/29
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GDWebServer {
    public static void main(String[] args) {
        SpringApplication.run(GDWebServer.class,args);
    }
}
