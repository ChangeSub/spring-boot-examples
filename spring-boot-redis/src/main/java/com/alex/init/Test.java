package com.alex.init;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-13 17:30
 */
@Component
public class Test implements CommandLineRunner {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("book","12312");
        //命名要规范
        //存取值
        redisTemplate.opsForValue().set("user:1000:book:12",jsonObject,1000, TimeUnit.MINUTES);

        JSONObject back = (JSONObject) redisTemplate.opsForValue().get("user:1000:book:12");

        System.out.println("back::"+back);
    }

}
