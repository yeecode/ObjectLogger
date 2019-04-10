package com.github.yeecode.objectLogger.server.bean;

import com.github.yeecode.objectLogger.client.bean.LocalTypeHandler;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import org.springframework.stereotype.Service;


@Service
public class LocalTypeHandlerBean implements LocalTypeHandler {

    public static final String USERID = "USERID";
    public static final String ATTACHMENT = "ATTACHMENT";


    @Override
    public ActionItemModel handleLocalType(String subLocalType, String oldValue, String newValue) {
        ActionItemModel logActionItemModel = new ActionItemModel();

        if (subLocalType.equals(USERID)) {
            logActionItemModel.setNewValue("AAAAAAAA");
            logActionItemModel.setOldValue("aa");
        } else {
            logActionItemModel = null;
        }
        return logActionItemModel;
    }


}
