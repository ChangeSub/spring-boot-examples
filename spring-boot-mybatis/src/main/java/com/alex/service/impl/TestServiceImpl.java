package com.alex.service.impl;

import com.alex.mapper.TestMapper;
import com.alex.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-13 17:13
 */
@Service
public class TestServiceImpl {

    @Autowired
    private TestMapper testMapper;

    public JSONObject listloginLog(JSONObject requestJson) {
        //整理成分页条件
        CommonUtil.fillPageParamAdmin(requestJson);

        List<JSONObject> list = testMapper.listloginLog(requestJson);

        int totalCount = testMapper.countListloginLog(requestJson);

        return CommonUtil.successPage(requestJson.getIntValue("pageRow"), list, totalCount);
    }
}
