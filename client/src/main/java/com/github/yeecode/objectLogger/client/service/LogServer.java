package com.github.yeecode.objectLogger.client.service;

import com.github.yeecode.objectLogger.client.http.JacksonUtils;
import com.github.yeecode.objectLogger.client.model.OperationModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModel) {
        return JacksonUtils.parseObject(operationModel, OperationModel.class);
    }
}
