package com.terminal.manage;

import com.alibaba.fastjson.JSON;
import com.terminal.manage.model.Menu;
import com.terminal.manage.services.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Servlet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private MenuService menuService;


    @Test
    void contextLoads() {

    }
}
