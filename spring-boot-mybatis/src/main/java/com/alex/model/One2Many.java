package com.alex.model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-03-26 10:44
 */
public class One2Many extends JSONObject {
    private List<JSONObject> patrol;
    private List<JSONObject> group;
    private List<JSONObject> detail;
    private List<JSONObject> users;
}
