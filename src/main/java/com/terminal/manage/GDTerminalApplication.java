package com.terminal.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Jyt
 * @date 2021/9/29
 */
//@EnableSwagger2
@MapperScan(basePackages = {"com.terminal.manage.mapper"})
@SpringBootApplication(scanBasePackages = "com.terminal.manage")
public class GDTerminalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GDTerminalApplication.class, args);
    }

}