package com.alex.config.exception;

import com.alex.constants.ErrorEnum;
import com.alex.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述:
 * 自定义异常
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 *
 * @author hany
 * @create 2018-11-20 9:01
 */
public class CommonJsonException extends RuntimeException {
    private JSONObject resultJson;

    public CommonJsonException(ErrorEnum errorEnum) {
        this.resultJson = CommonUtil.errorJson(errorEnum);
    }

    public CommonJsonException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }
}
