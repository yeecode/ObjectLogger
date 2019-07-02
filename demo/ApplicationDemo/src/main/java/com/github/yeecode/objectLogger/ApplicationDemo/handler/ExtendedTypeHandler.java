package com.github.yeecode.objectLogger.ApplicationDemo.handler;

import com.github.yeecode.objectLogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectLogger.client.model.BaseActionItemModel;
import org.springframework.stereotype.Service;

@Service
public class ExtendedTypeHandler implements BaseExtendedTypeHandler {
    @Override
    public BaseActionItemModel handleAttributeChange(String extendedType, String attributeName, String logTagName, Object oldValue, Object newValue) {
        BaseActionItemModel baseActionItemModel = new BaseActionItemModel();
        if (extendedType.equals("userIdType")) {
            baseActionItemModel.setOldValue("USER_" + oldValue);
            baseActionItemModel.setNewValue("USER_" + newValue);
            baseActionItemModel.setDiffValue(oldValue + "->" + newValue);
        }
        return baseActionItemModel;
    }
}
