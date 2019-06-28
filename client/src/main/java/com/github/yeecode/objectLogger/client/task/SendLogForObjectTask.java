package com.github.yeecode.objectLogger.client.task;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfigBean;
import com.github.yeecode.objectLogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectLogger.client.http.HttpBean;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import com.github.yeecode.objectLogger.client.model.ActionModel;
import com.github.yeecode.objectLogger.client.model.BaseActionItemModel;
import com.github.yeecode.objectLogger.client.wrapper.FieldWrapper;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class SendLogForObjectTask implements Runnable {
    private BaseExtendedTypeHandler baseExtendedTypeHandler;
    private Integer objectId;
    private String actor;
    private String action;
    private String actionName;
    private String extraWords;
    private String comment;
    private Object oldObject;
    private Object newObject;
    private ObjectLoggerConfigBean objectLoggerConfigBean;
    private HttpBean httpBean;


    public SendLogForObjectTask(Integer objectId, String actor, String action, String actionName,
                                String extraWords, String comment,
                                Object oldObject, Object newObject, ObjectLoggerConfigBean objectLoggerConfigBean,
                                HttpBean httpBean, BaseExtendedTypeHandler baseExtendedTypeHandler) {
        this.objectId = objectId;
        this.actor = actor;
        this.action = action;
        this.actionName = actionName;
        this.extraWords = extraWords;
        this.comment = comment;
        this.oldObject = oldObject;
        this.newObject = newObject;
        this.objectLoggerConfigBean = objectLoggerConfigBean;
        this.httpBean = httpBean;
        this.baseExtendedTypeHandler = baseExtendedTypeHandler;
    }

    @Override
    public void run() {
        try {
            // 基本Action
            ActionModel actionModel = new ActionModel(objectLoggerConfigBean.getAppName(), oldObject.getClass().getSimpleName(),
                    objectId, actor, action, actionName, extraWords, comment, new Date());

            // Action中的attributes
            Class modelClazz = newObject.getClass();
            Class oldModelClazz = oldObject.getClass();
            if (oldModelClazz.equals(modelClazz)) {
                Field[] fields = modelClazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    FieldWrapper fieldWrapper = new FieldWrapper(field, field.get(oldObject), field.get(newObject));
                    if (fieldWrapper.isWithLogTag()) {
                        // 对于不存在LogDescription注解的字段，直接忽略不处理
                        if (!fieldWrapper.getOldValue().equals(fieldWrapper.getNewValue())) {
                            BaseActionItemModel baseActionItemModel;
                            if (fieldWrapper.isWithExtendedType()) {
                                baseActionItemModel = handleExtendedTypeItem(fieldWrapper);
                            } else {
                                baseActionItemModel = handleBuiltinTypeItem(fieldWrapper);
                            }

                            if (baseActionItemModel != null) {
                                actionModel.getActionItemModelList().add((ActionItemModel) baseActionItemModel);
                            }
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(actionModel.getActionItemModelList())) {
                httpBean.sendLog(JSON.toJSONString(actionModel));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BaseActionItemModel handleBuiltinTypeItem(FieldWrapper fieldWrapper) {
        BaseActionItemModel handlerOutput = fieldWrapper.getLogTag().builtinType().handlerAttributeChange(fieldWrapper);
        if (handlerOutput != null) {
            BaseActionItemModel baseActionItemModel = new ActionItemModel();
            // 固定值
            baseActionItemModel.setAttribute(fieldWrapper.getAttributeName());
            baseActionItemModel.setAttributeName(fieldWrapper.getDisplayName());
            baseActionItemModel.setAttributeType(fieldWrapper.getLogTag().builtinType().name());
            // 自定义类型返回的值
            baseActionItemModel.setOldValue(handlerOutput.getOldValue());
            baseActionItemModel.setNewValue(handlerOutput.getNewValue());
            baseActionItemModel.setDiffValue(handlerOutput.getDiffValue());
            return baseActionItemModel;
        } else {
            return null;
        }
    }

    private BaseActionItemModel handleExtendedTypeItem(FieldWrapper fieldWrapper) {
        BaseActionItemModel baseActionItemModel = baseExtendedTypeHandler.handleAttributeChange(
                fieldWrapper.getAttributeName(),
                fieldWrapper.getLogTagName(),
                fieldWrapper.getOldValue(),
                fieldWrapper.getNewValue()
        );

        if (baseActionItemModel.getAttributeType() == null) {
            baseActionItemModel.setAttributeType(fieldWrapper.getExtendedType());
        }
        if (baseActionItemModel.getAttribute() == null) {
            baseActionItemModel.setAttribute(fieldWrapper.getAttributeName());
        }
        if (baseActionItemModel.getAttributeName() == null) {
            baseActionItemModel.setAttributeName(fieldWrapper.getDisplayName());
        }

        return baseActionItemModel;
    }
}
