package com.github.yeecode.objectLogger.client.service;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.model.OperationModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModel) {
        return JSON.parseObject(operationModel, OperationModel.class);
    }
}
