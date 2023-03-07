package com.my.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/7 18:24
 * @Version:
 */
@SpringBootApplication
@MapperScan("com.my.study.mapper")
public class MybatisPlusTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusTestApplication.class, args);
    }
}
