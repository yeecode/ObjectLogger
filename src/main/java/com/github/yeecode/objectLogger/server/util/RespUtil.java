package com.github.yeecode.objectLogger.server.util;

import com.github.yeecode.objectLogger.server.constant.RespConstant;

import java.util.HashMap;
import java.util.Map;

public class RespUtil {

    /**
     * 工具类不可以实例化
     */
    private RespUtil() {
    }

    /**
     * 生成正确的返回Map
     *
     * @return 返回Map
     */
    public static Map<String, Object> getSuccessMap() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.SUCCESS);
        returnMap.put(RespConstant.RESP_MSG_KEY, "成功");
        return returnMap;
    }

    public static Map<String, Object> getSuccessMap(Object respData) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.SUCCESS);
        returnMap.put(RespConstant.RESP_MSG_KEY, "成功");
        returnMap.put(RespConstant.RESP_MSG_DATA, respData);
        return returnMap;
    }

    /**
     * 生成一般错误的返回Map
     *
     * @param respMsg 返回描述
     * @return 返回Map
     */
    public static Map<String, Object> getCommonErrorMap(String respMsg) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.COMMON_ERROR);
        returnMap.put(RespConstant.RESP_MSG_KEY, respMsg);
        return returnMap;
    }

}
