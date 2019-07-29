package com.github.yeecode.logger.server.util;

import com.github.yeecode.logger.server.constant.RespConstant;

import java.util.HashMap;
import java.util.Map;

public class RespUtil {

    private RespUtil() {
    }

    public static Map<String, Object> getSuccessMap() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.SUCCESS);
        returnMap.put(RespConstant.RESP_MSG_KEY, "SUCCESS");
        return returnMap;
    }

    public static Map<String, Object> getSuccessMap(Object respData) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.SUCCESS);
        returnMap.put(RespConstant.RESP_MSG_KEY, "SUCCESS");
        returnMap.put(RespConstant.RESP_MSG_DATA, respData);
        return returnMap;
    }

    public static Map<String, Object> getCommonErrorMap(String respMsg) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(RespConstant.RESP_CODE_KEY, RespConstant.COMMON_ERROR);
        returnMap.put(RespConstant.RESP_MSG_KEY, respMsg);
        return returnMap;
    }

}
