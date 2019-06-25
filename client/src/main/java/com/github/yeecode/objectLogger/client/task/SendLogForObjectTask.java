package com.github.yeecode.objectLogger.client.task;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.annotation.LogDescription;
import com.github.yeecode.objectLogger.client.bean.ObjectLoggerConfigBean;
import com.github.yeecode.objectLogger.client.bean.HttpBean;
import com.github.yeecode.objectLogger.client.bean.LocalTypeHandler;
import com.github.yeecode.objectLogger.client.constant.AttributeTypeEnum;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import com.github.yeecode.objectLogger.client.model.ActionModel;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class SendLogForObjectTask implements Runnable {
    private LocalTypeHandler localTypeHandler;
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
                                HttpBean httpBean, LocalTypeHandler localTypeHandler) {
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
        this.localTypeHandler = localTypeHandler;
    }

    @Override
    public void run() {
        try {
            // 基本Action
            ActionModel actionModel = new ActionModel(objectLoggerConfigBean.getAppName(), oldObject.getClass().getSimpleName(),
                    objectId,actor,action,actionName,extraWords,comment,new Date());

            // Action中的attributes
            Class modelClazz = newObject.getClass();
            Class oldModelClazz = oldObject.getClass();
            if (oldModelClazz.equals(modelClazz)) {
                Field[] fields = modelClazz.getDeclaredFields();
                for (Field field : fields) {
                    LogDescription logDescription = field.getAnnotation(LogDescription.class);
                    if (logDescription != null) {
                        // 对于不存在LogDescription注解的字段，直接忽略不处理
                        field.setAccessible(true);
                        String oldValue = field.get(oldObject) != null ? JSON.toJSONString(field.get(oldObject)) : JSON.toJSONString("");
                        String newValue = field.get(newObject) != null ? JSON.toJSONString(field.get(newObject)) : JSON.toJSONString("");

                        if (!oldValue.equals(newValue)) {
                            ActionItemModel actionItemModel = new ActionItemModel();

                            String attribute = field.getName();
                            actionItemModel.setAttribute(attribute);
                            actionItemModel.setAttributeName(logDescription.name().length() == 0
                                    ? attribute : logDescription.name());

                            ActionItemModel handlerOutput = logDescription.type().handler(localTypeHandler, logDescription.localType(), oldValue, newValue);
                            if (handlerOutput != null) {
                                actionItemModel.setOldValue(handlerOutput.getOldValue());
                                actionItemModel.setNewValue(handlerOutput.getNewValue());
                                actionItemModel.setDiffValue(handlerOutput.getDiffValue());
                                actionItemModel.setAttributeType(
                                        logDescription.type().equals(AttributeTypeEnum.LOCALTYPE) ?
                                                logDescription.localType() : logDescription.type().name());

                                actionModel.getActionItemModelList().add(actionItemModel);
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
}
