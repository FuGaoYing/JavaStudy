package com.my.study.redissiontest;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class RedissionTestApplicationTests {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
        RLock test = redissonClient.getLock("test");

        boolean lock = test.tryLock();

    }
    @Test
    void test1() {
        String string = Objects.requireNonNull(redisTemplate.opsForValue().get("test")).toString();
        System.out.println(string);
    }

}
