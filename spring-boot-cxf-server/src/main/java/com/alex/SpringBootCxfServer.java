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
public class SpringBootCxfServer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCxfServer.class, args);
    }
}
