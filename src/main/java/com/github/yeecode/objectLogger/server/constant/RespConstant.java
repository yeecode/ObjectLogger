package com.github.yeecode.objectLogger.server.constant;

public class RespConstant {
    // resp回应的Key
    public static final String RESP_CODE_KEY = "respCode";
    public static final String RESP_MSG_KEY = "respMsg";
    public static final String RESP_MSG_DATA = "respData";

    // respCode
    public static final String SUCCESS = "1000"; // 成功
    public static final String COMMON_ERROR = "1001"; // 失败

    // respMsg内容，返回提示语-数据库相关
    public static final String QUERY_EXCEPTION = "记录查询异常。";
    public static final String DELETE_EXCEPTION = "记录删除异常。";
    public static final String INSERT_EXCEPTION = "记录插入异常。";
    public static final String UPDATE_EXCEPTION = "记录更新异常。";
    public static final String REPEAT_EXCEPTION = "重复字段异常";
}
