package com.github.yeecode.objectLogger.server.bean;

import com.github.yeecode.objectLogger.client.bean.LocalTypeHandler;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import org.springframework.stereotype.Service;


@Service
public class LocalTypeHandlerBean implements LocalTypeHandler {

    @Override
    public ActionItemModel handleLocalType(String subLocalType, String oldValue, String newValue) {
        return null;
    }
}
