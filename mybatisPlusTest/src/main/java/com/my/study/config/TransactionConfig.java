package com.my.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/7 23:13
 * @Version:
 */
@Configuration
public class TransactionConfig {

    @Bean
    public TransactionTemplate transactionTemplate(){
        TransactionTemplate template = new TransactionTemplate();
        // 超时时间
        template.setTimeout(3000);
        // 隔离机别 默认 -1
        template.setIsolationLevel(-1);
        // 传播行为
        template.setPropagationBehavior(0);
        return template;
    }
}
