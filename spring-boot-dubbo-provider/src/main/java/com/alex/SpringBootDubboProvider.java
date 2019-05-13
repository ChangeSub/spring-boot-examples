package com.alex;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-10 15:26
 */
@EnableDubbo(scanBasePackages = "com.alex")	//开启基于注解的dubbo功能
@EnableHystrix
@SpringBootApplication
public class SpringBootDubboProvider {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboProvider.class, args);
    }
}
