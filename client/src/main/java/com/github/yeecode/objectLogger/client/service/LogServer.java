package com.github.yeecode.objectLogger.client.service;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.model.ActionModel;
import org.springframework.stereotype.Component;

@Component
public class LogServer {

    public ActionModel resolveActionModel(String actionModelJson) {
        return JSON.parseObject(actionModelJson, ActionModel.class);
    }
}
