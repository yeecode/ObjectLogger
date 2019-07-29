package com.github.yeecode.objectLogger.client.service;

import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfig;
import com.github.yeecode.objectLogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectLogger.client.http.HttpUtil;
import com.github.yeecode.objectLogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectLogger.client.task.LogAttributesTask;
import com.github.yeecode.objectLogger.client.task.LogObjectTask;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class LogClient {
    private ObjectLoggerConfig objectLoggerConfig;
    private BaseExtendedTypeHandler baseExtendedTypeHandler;
    private ExecutorService fixedThreadPool;

    public LogClient(ObjectLoggerConfig objectLoggerConfig, BaseExtendedTypeHandler baseExtendedTypeHandler, ExecutorService fixedThreadPool) {
        this.objectLoggerConfig = objectLoggerConfig;
        this.baseExtendedTypeHandler = baseExtendedTypeHandler;
        this.fixedThreadPool = fixedThreadPool;

    }


    /**
     * Auto diff old/new object and write one log
     * Attention: the attributes be diffed must with @LogTag
     *
     * @param objectId       required
     * @param operator       required
     * @param operationName  operationName
     * @param operationAlias operation alias for display
     * @param extraWords     extra description for operation
     * @param comment        comment for operation
     * @param oldObject      required,the object before operation
     * @param newObject      required,the object after operation
     */
    public void logObject(Integer objectId, String operator, String operationName, String operationAlias,
                          String extraWords, String comment,
                          Object oldObject, Object newObject) {
        try {
            LogObjectTask logObjectTask = new LogObjectTask(objectId, operator, operationName, operationAlias,
                    extraWords, comment, oldObject, newObject, objectLoggerConfig,  baseExtendedTypeHandler);
            fixedThreadPool.execute(logObjectTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Write log with items
     *
     * @param objectName             required,the object alias
     * @param objectId               required,the object id
     * @param operator               required
     * @param operationName          operationName
     * @param operationAlias         operation alias for display
     * @param extraWords             extra description for operation
     * @param comment                comment for operation
     * @param baseAttributeModelList attributes list:
     *                               required: attributeType，attribute，attributeName
     *                               optional: oldValue，newValue,diffValue
     */
    public void logAttributes(String objectName, Integer objectId,
                              String operator, String operationName, String operationAlias,
                              String extraWords, String comment,
                              List<BaseAttributeModel> baseAttributeModelList) {
        try {


            LogAttributesTask logAttributesTask = new LogAttributesTask(objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, baseAttributeModelList, objectLoggerConfig);
            fixedThreadPool.execute(logAttributesTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
