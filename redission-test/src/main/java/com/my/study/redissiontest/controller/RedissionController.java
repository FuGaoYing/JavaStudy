package com.my.study.redissiontest.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/7 15:46
 * @Version:
 */
@RestController
@Slf4j
@RequestMapping("/redis")
public class RedissionController {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PutMapping("/set")
    public JSONObject test1(@RequestBody JSONObject jsonObject) {
        redisTemplate.opsForValue().set("test", jsonObject);
        return JSON.parseObject("success");
    }


}
