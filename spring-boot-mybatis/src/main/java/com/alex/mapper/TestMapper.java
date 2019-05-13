package com.alex.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-05-10 15:30
 */
@Mapper
public interface TestMapper {
    List<JSONObject> getList(JSONObject requestJson);


    /**
     * 多层一对多示例
     * @param requestJson
     * @return
     */
    List<JSONObject> queryPatrolByTaskGuid(JSONObject requestJson);

    List<JSONObject> listloginLog(JSONObject requestJson);

    int countListloginLog(JSONObject requestJson);
}
