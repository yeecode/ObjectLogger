package com.github.yeecode.objectLogger.server.constant;

public class RespConstant {
    // respKey
    public static final String RESP_CODE_KEY = "respCode";
    public static final String RESP_MSG_KEY = "respMsg";
    public static final String RESP_MSG_DATA = "respData";

    // respCode
    public static final String SUCCESS = "1000"; // SUCCESS
    public static final String COMMON_ERROR = "1001"; // FAIL

    // respMsg内容，返回提示语-数据库相关
    public static final String QUERY_EXCEPTION = "QUERY EXCEPTION";
    public static final String DELETE_EXCEPTION = "DELETE EXCEPTION";
    public static final String INSERT_EXCEPTION = "INSERT EXCEPTION";
    public static final String UPDATE_EXCEPTION = "UPDATE EXCEPTION";
    public static final String REPEAT_EXCEPTION = "REPEAT EXCEPTION";
}
