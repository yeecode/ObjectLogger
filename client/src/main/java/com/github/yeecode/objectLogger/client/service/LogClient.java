package com.github.yeecode.objectlogger.client.service;

import com.github.yeecode.objectlogger.client.config.ObjectLoggerConfig;
import com.github.yeecode.objectlogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectlogger.client.http.HttpUtil;
import com.github.yeecode.objectlogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectlogger.client.task.LogAttributesTask;
import com.github.yeecode.objectlogger.client.task.LogObjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class LogClient {
    @Autowired
    private ObjectLoggerConfig objectLoggerConfig;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired(required = false)
    private BaseExtendedTypeHandler baseExtendedTypeHandler;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    /**
     * Auto diff old/new object and write one log
     * Attention: the attributes be diffed must with @LogTag
     *
     * @param objectId          required
     * @param operator          required
     * @param operationName     operationName
     * @param operationAlias    operation alias for display
     * @param extraWords        extra description for operation
     * @param comment           comment for operation
     * @param oldObject         required,the object before operation
     * @param newObject         required,the object after operation
     */
    public void logObject(Integer objectId, String operator, String operationName, String operationAlias,
                                 String extraWords, String comment,
                                 Object oldObject, Object newObject) {
        try {
            LogObjectTask logObjectTask = new LogObjectTask(objectId, operator, operationName, operationAlias,
                    extraWords, comment, oldObject, newObject, objectLoggerConfig, httpUtil, baseExtendedTypeHandler);
            fixedThreadPool.execute(logObjectTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Write log with items
     *
     * @param objectName              required,the object alias
     * @param objectId                required,the object id
     * @param operator                required
     * @param operationName           operationName
     * @param operationAlias          operation alias for display
     * @param extraWords              extra description for operation
     * @param comment                 comment for operation
     * @param baseAttributeModelList  attributes list:
     *                                required: attributeType，attribute，attributeName
     *                                optional: oldValue，newValue,diffValue
     */
    public void logAttributes(String objectName, Integer objectId,
                                String operator, String operationName, String operationAlias,
                                String extraWords, String comment,
                                List<BaseAttributeModel> baseAttributeModelList) {
        try {


            LogAttributesTask logAttributesTask = new LogAttributesTask(objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, baseAttributeModelList, objectLoggerConfig, httpUtil);
            fixedThreadPool.execute(logAttributesTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
