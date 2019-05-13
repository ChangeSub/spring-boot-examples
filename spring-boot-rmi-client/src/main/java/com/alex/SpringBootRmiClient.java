package com.alex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-04-30 11:26
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootRmiClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRmiClient.class, args);
    }
}
