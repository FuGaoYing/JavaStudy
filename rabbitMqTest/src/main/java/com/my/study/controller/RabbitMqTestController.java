package com.my.study.controller;

import com.alibaba.fastjson2.JSONObject;
import com.my.study.producer.MyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/6 18:58
 * @Version:
 */
@RestController
@RequestMapping("/mq")
public class RabbitMqTestController {

    @Autowired
    MyProducer producer;

    @PostMapping("/test1")
    public String test1(@RequestBody JSONObject data) {
        System.out.println(data.toString());
        producer.send("routingKey1",data.toString());
        return "success";
    }
}
