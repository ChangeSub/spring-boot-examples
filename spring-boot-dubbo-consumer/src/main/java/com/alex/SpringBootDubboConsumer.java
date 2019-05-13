package com.alex;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-10 15:26
 */
@EnableDubbo
@EnableHystrix
@SpringBootApplication
public class SpringBootDubboConsumer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboConsumer.class, args);
    }
}
