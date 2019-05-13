package com.alex.config.exception;

import com.alex.constants.ErrorEnum;
import com.alex.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述:
 *
 * @author hany
 * @create 2018-11-25 15:56
 */
public class AccessAuthException extends RuntimeException  {
    private JSONObject resultJson;

    public AccessAuthException(ErrorEnum errorEnum) {
        this.resultJson = CommonUtil.errorJson(errorEnum);
    }

    public AccessAuthException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }
}
