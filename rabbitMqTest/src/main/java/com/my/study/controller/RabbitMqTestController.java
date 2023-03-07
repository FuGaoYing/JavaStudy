package com.my.study.controller;

import com.alibaba.fastjson2.JSONObject;
import com.my.study.producer.MyProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/6 18:58
 * @Version:
 */
@RestController
@RequestMapping("/mq")
@Slf4j
public class RabbitMqTestController {
    @Autowired
    MyProducer producer;

    @PostMapping("/test1")
    public String test1(@RequestBody JSONObject data) {
        log.info("入参 {}", data.toString());
        producer.send("routingKey1",data.toString());
        return "success";
    }
    @GetMapping("/test2")
    public String test2() {
        log.info("test2执行");
        producer.send("routingKey1","hello word");
        return "success";
    }
}
