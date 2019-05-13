package com.alex.controller;

import com.alex.service.impl.TestServiceImpl;
import com.alex.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-13 17:10
 */
@RestController
@RequestMapping("/system")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    /**
     * 前台则直接请求json
     *  contentType: "application/json"
     *
     * @param requestJson
     * @return
     */
    @PostMapping("/loginLog/list")
    public JSONObject listloginLog(@RequestBody JSONObject requestJson) {
        //必传参数为limit,page，否则报错
        CommonUtil.hasAllRequired(requestJson, "limit,page");
        return testService.listloginLog(requestJson);
    }


}
