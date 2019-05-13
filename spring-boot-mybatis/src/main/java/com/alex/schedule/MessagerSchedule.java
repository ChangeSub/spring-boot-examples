package com.alex.schedule;

import com.alex.mapper.TestMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-05 14:58
 */

@Component
@EnableAsync
public class MessagerSchedule {

    @Autowired
    private TestMapper testMapper;

    @Async
    @Scheduled(fixedDelay = 2*1000)
    public void test() throws IOException {

        JSONObject requestJson = new JSONObject();
        requestJson.put("param1","value1");

        List<JSONObject> list = testMapper.getList(requestJson);

    }

}
