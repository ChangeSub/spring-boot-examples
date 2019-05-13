package com.alex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-10 15:26
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootMybatis {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatis.class, args);
    }
}
