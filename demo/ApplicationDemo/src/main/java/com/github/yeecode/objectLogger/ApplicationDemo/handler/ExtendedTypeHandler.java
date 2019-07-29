package com.github.yeecode.objectLogger.ApplicationDemo.handler;

import com.github.yeecode.logger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.logger.client.model.BaseAttributeModel;
import org.springframework.stereotype.Service;

@Service
public class ExtendedTypeHandler implements BaseExtendedTypeHandler {
    @Override
    public BaseAttributeModel handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue) {
        BaseAttributeModel baseAttributeModel = new BaseAttributeModel();
        if (extendedType.equals("userIdType")) {
            baseAttributeModel.setOldValue("USER_" + oldValue);
            baseAttributeModel.setNewValue("USER_" + newValue);
            baseAttributeModel.setDiffValue(oldValue + "->" + newValue);
        }
        return baseAttributeModel;
    }
}
