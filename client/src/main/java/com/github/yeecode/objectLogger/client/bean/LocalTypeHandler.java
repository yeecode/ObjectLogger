package com.github.yeecode.objectLogger.client.bean;


import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import org.springframework.stereotype.Component;

@Component
public interface LocalTypeHandler {
    ActionItemModel handleLocalType(String localType, String oldValue, String newValue);
}
