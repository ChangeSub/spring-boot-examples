package com.alex.util;

import com.alex.config.exception.AccessAuthException;
import com.alex.config.exception.CommonJsonException;
import com.alex.constants.Constants;
import com.alex.constants.ErrorEnum;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: hany
 * @description: 本后台接口系统常用的json工具类
 * @date: 2018/08/24 10:12
 */
public class CommonUtil {

    /**
     * 返回一个returnData为空对象的成功消息的json
     *
     * @return
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为100的json
     *
     * @param returnData json里的主要内容
     * @return
     */
    public static JSONObject successJson(Object returnData) {
        JSONObject resultJson = new JSONObject();
//        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("msg", Constants.SUCCESS_MSG);
        resultJson.put("data", returnData);
        return resultJson;
    }

    public static JSONObject successJsonApp(Object returnData) {
        JSONObject resultJson = new JSONObject();
//        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("code", Constants.SUCCESS_CODE_APP);
        resultJson.put("msg", Constants.SUCCESS_MSG);
        resultJson.put("data", returnData);
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     *
     * @param errorEnum 错误码的errorEnum
     * @return
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("msg", errorEnum.getErrorMsg());
        resultJson.put("data", new JSONObject());
        return resultJson;
    }



    /**
     * 查询分页结果后的封装工具方法
     *
     * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
     * @param list        查询分页对象list
     * @param totalCount  查询出记录的总条数
     */
    public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, int totalCount) {
        int pageRow = requestJson.getIntValue("pageRow");
        int totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        returnData.put("count", totalCount);
        returnData.put("totalPage", totalPage);
        result.put("data", returnData);
        return result;
    }

    public static JSONObject successPageAccount(final JSONObject requestJson, List<JSONObject> list, int totalCount, String flag) {
        int pageRow = requestJson.getIntValue("pageRow");
        int totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        returnData.put("count", totalCount);
        returnData.put("totalPage", totalPage);
        result.put("data", returnData);
        result.put("flag", flag);
        return result;
    }

    /**
     * 查询分页结果后的封装工具方法(为了兼容layuiAdmin)
     *
     * @param pageRow    显示的数量
     * @param list       查询分页对象list
     * @param totalCount 查询出记录的总条数
     */
    public static JSONObject successPage(Integer pageRow, Object list, int totalCount) {
        JSONObject result = successJson();
        result.put("code", Constants.SUCCESS_CODE);
        result.put("msg", Constants.SUCCESS_MSG);
        result.put("data", list);
        result.put("count", totalCount);
        return result;
    }

    /**
     * 查询分页结果后的封装工具方法(为了兼容layuiAdmin)
     *
     * @param list 查询分页对象list
     */
    public static JSONObject successPageLay(List<JSONObject> list) {
        JSONObject result = successJson();
        result.put("code", Constants.SUCCESS_CODE);
        result.put("msg", Constants.SUCCESS_MSG);
        result.put("data", list);
        return result;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param list 查询分页对象list
     */
    public static JSONObject successPage(List<JSONObject> list) {
        JSONObject result = successJson();
        JSONObject returnData = new JSONObject();
        returnData.put("list", list);
        result.put("data", returnData);
        return result;
    }

    /**
     * 获取总页数
     *
     * @param pageRow   每页行数
     * @param itemCount 结果的总条数
     * @return
     */
    public static int getPageCounts(int pageRow, int itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageRow > 0 ?
                itemCount / pageRow + 1 :
                itemCount / pageRow;
    }

    /**
     * 将request参数值转为json
     *
     * @param request
     * @return
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     *
     * @param request
     * @param requiredColumns
     * @return
     */
/*    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }*/

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     * @param jsonObject
     * @return
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringTools.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringTools.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringTools.isNullOrEmpty(missCol)) {
                jsonObject.clear();
                jsonObject.put("code", ErrorEnum.E_90003.getErrorCode());
                jsonObject.put("msg", "缺少必填参数:" + missCol.trim());
                jsonObject.put("data", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        }
    }

    public static void authError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ErrorEnum.E_1001.getErrorCode());
        jsonObject.put("msg", "登陆已过期,请重新登陆");
        jsonObject.put("data", new JSONObject());
        throw new AccessAuthException(jsonObject);
    }
    public static JSONObject error(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("msg", errorEnum.getErrorMsg());
        resultJson.put("data", new JSONObject());
        throw new CommonJsonException(resultJson);
    }

    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param paramObject    查询条件json
     * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
     */
    public static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    public static void fillPageParamAdmin(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("page");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("limit");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    public static void fillPageParamAdmin(final JSONObject paramObject) {
        fillPageParamAdmin(paramObject, 10);
    }
    /**
     * 分页查询之前的处理参数
     * 没有传pageRow参数时,默认每页10条.
     *
     * @param paramObject
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }
}
