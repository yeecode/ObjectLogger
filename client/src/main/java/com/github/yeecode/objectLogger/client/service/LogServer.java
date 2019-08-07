package com.github.yeecode.objectlogger.client.service;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectlogger.client.model.OperationModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModel) {
        return JSON.parseObject(operationModel, OperationModel.class);
    }
}
