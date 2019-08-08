package com.github.yeecode.objectlogger.client.service;

import com.github.yeecode.objectlogger.client.model.OperationModel;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public OperationModel resolveOperationModel(String operationModelString) {
        return new Gson().fromJson(operationModelString,OperationModel.class);
    }
}
