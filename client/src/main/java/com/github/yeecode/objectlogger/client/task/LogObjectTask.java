package com.github.yeecode.objectlogger.client.task;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectlogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectlogger.client.model.OperationModel;
import com.github.yeecode.objectlogger.client.wrapper.ClazzWrapper;
import com.github.yeecode.objectlogger.client.wrapper.FieldWrapper;
import com.github.yeecode.objectlogger.client.config.ObjectLoggerConfig;
import com.github.yeecode.objectlogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectlogger.client.handler.BuiltinTypeHandler;
import com.github.yeecode.objectlogger.client.http.HttpUtil;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class LogObjectTask implements Runnable {
    private BaseExtendedTypeHandler baseExtendedTypeHandler;
    private Integer objectId;
    private String operator;
    private String operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private Object oldObject;
    private Object newObject;
    private ObjectLoggerConfig objectLoggerConfig;
    private HttpUtil httpUtil;


    public LogObjectTask(Integer objectId, String operator, String operationName, String operationAlias,
                         String extraWords, String comment,
                         Object oldObject, Object newObject, ObjectLoggerConfig objectLoggerConfig,
                         HttpUtil httpUtil, BaseExtendedTypeHandler baseExtendedTypeHandler) {
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.oldObject = oldObject;
        this.newObject = newObject;
        this.objectLoggerConfig = objectLoggerConfig;
        this.httpUtil = httpUtil;
        this.baseExtendedTypeHandler = baseExtendedTypeHandler;
    }

    @Override
    public void run() {
        try {
            // 基本Action
            OperationModel operationModel = new OperationModel(objectLoggerConfig.getBusinessAppName(), oldObject.getClass().getSimpleName(),
                    objectId, operator, operationName, operationAlias, extraWords, comment, new Date());

            // Action中的attributes
            Class modelClazz = newObject.getClass();
            Class oldModelClazz = oldObject.getClass();
            if (oldModelClazz.equals(modelClazz)) {
                ClazzWrapper clazzWrapper = new ClazzWrapper(modelClazz); // issue #1
                List<Field> fieldList = clazzWrapper.getFieldList();
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    FieldWrapper fieldWrapper = new FieldWrapper(field, field.get(oldObject), field.get(newObject));
                    if (fieldWrapper.isWithLogTag() || "true".equals(objectLoggerConfig.getAutoLogAttributes())) {
                        if (!nullableEquals(fieldWrapper.getOldValue(), fieldWrapper.getNewValue())) {
                            BaseAttributeModel baseAttributeModel;
                            if (fieldWrapper.isWithExtendedType()) {
                                baseAttributeModel = handleExtendedTypeItem(fieldWrapper);
                            } else {
                                baseAttributeModel = handleBuiltinTypeItem(fieldWrapper);
                            }

                            if (baseAttributeModel != null) {
                                operationModel.addBaseActionItemModel(baseAttributeModel);
                            }
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(operationModel.getAttributeModelList())) {
                httpUtil.sendLog(JSON.toJSONString(operationModel));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BaseAttributeModel handleBuiltinTypeItem(FieldWrapper fieldWrapper) {
        BuiltinTypeHandler builtinType = BuiltinTypeHandler.NORMAL;
        if (fieldWrapper.getLogTag() != null) {
            builtinType = fieldWrapper.getLogTag().builtinType();
        }

        BaseAttributeModel handlerOutput = builtinType.handlerAttributeChange(fieldWrapper);

        if (handlerOutput != null) {
            // 固定值
            handlerOutput.setAttributeName(fieldWrapper.getAttributeName());
            handlerOutput.setAttributeAlias(fieldWrapper.getAttributeAlias());
            handlerOutput.setAttributeType(builtinType.name());
            return handlerOutput;
        } else {
            return null;
        }
    }

    private BaseAttributeModel handleExtendedTypeItem(FieldWrapper fieldWrapper) {
        BaseAttributeModel baseAttributeModel = baseExtendedTypeHandler.handleAttributeChange(
                fieldWrapper.getExtendedType(),
                fieldWrapper.getAttributeName(),
                fieldWrapper.getAttributeAlias(),
                fieldWrapper.getOldValue(),
                fieldWrapper.getNewValue()
        );

        if (baseAttributeModel != null) {
            if (baseAttributeModel.getAttributeType() == null) {
                baseAttributeModel.setAttributeType(fieldWrapper.getExtendedType());
            }
            if (baseAttributeModel.getAttributeName() == null) {
                baseAttributeModel.setAttributeName(fieldWrapper.getAttributeName());
            }
            if (baseAttributeModel.getAttributeAlias() == null) {
                baseAttributeModel.setAttributeAlias(fieldWrapper.getAttributeAlias());
            }
        }

        return baseAttributeModel;
    }

    // issue #2
    private boolean nullableEquals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}
