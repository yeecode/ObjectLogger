package com.github.yeecode.objectLogger.client.handler;


import com.github.yeecode.objectLogger.client.model.BaseAttributeModel;
import org.springframework.stereotype.Component;

@Component
public interface BaseExtendedTypeHandler {
    BaseAttributeModel handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue);
}
