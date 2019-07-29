package com.github.yeecode.logger.client.service;

import com.github.yeecode.logger.client.http.JacksonUtils;
import com.github.yeecode.logger.client.model.OperationModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModel) {
        return JacksonUtils.parseObject(operationModel, OperationModel.class);
    }
}
