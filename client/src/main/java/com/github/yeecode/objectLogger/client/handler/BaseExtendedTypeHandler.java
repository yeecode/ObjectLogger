package com.github.yeecode.objectLogger.client.handler;


import com.github.yeecode.objectLogger.client.model.BaseActionItemModel;
import org.springframework.stereotype.Component;

@Component
public interface BaseExtendedTypeHandler {
    BaseActionItemModel handleAttributeChange(String attributeName, String logTagName, Object oldValue, Object newValue);
}
