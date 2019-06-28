package com.github.yeecode.objectLogger.client.service;

import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfigBean;
import com.github.yeecode.objectLogger.client.http.HttpBean;
import com.github.yeecode.objectLogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import com.github.yeecode.objectLogger.client.task.SendLogForObjectTask;
import com.github.yeecode.objectLogger.client.task.SendLogForItemsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class LogClient {
    @Autowired
    private ObjectLoggerConfigBean objectLoggerConfigBean;
    @Autowired
    private HttpBean httpBean;
    @Autowired(required = false)
    private BaseExtendedTypeHandler baseExtendedTypeHandler;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    /**
     * Auto diff old/new object and write one log
     * Attention: the attributes be diffed must with @LogTag
     *
     * @param objectId   required,objectId
     * @param actor      required,actor
     * @param action     action
     * @param actionName action name for display
     * @param extraWords extra description for action
     * @param comment    comment for action or log
     * @param oldObject   required,the object before action
     * @param newObject   required,the object after action
     */
    public void sendLogForObject(Integer objectId, String actor, String action, String actionName,
                                 String extraWords, String comment,
                                 Object oldObject, Object newObject) {
        try {
            SendLogForObjectTask sendLogForObjectTask = new SendLogForObjectTask(objectId, actor, action, actionName,
                    extraWords, comment, oldObject, newObject, objectLoggerConfigBean, httpBean, baseExtendedTypeHandler);
            fixedThreadPool.execute(sendLogForObjectTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Write log with items
     *
     * @param objectName           required,the object name
     * @param objectId             required,the object id
     * @param actor                required,actor
     * @param action               action
     * @param actionName           action name for display
     * @param extraWords           extra description for action
     * @param comment               comment for action or log
     * @param actionItemModelList   attributes list:
     *                               required: attributeType，attribute，attributeName
     *                               optional: oldValue，newValue,diffValue
     *                               leave null : id, actionId
     */
    public void sendLogForItems(String objectName, Integer objectId,
                                String actor, String action, String actionName,
                                String extraWords, String comment,
                                List<ActionItemModel> actionItemModelList) {
        try {
            SendLogForItemsTask sendLogForItemsTask = new SendLogForItemsTask(objectName, objectId, actor,
                    action, actionName, extraWords, comment, actionItemModelList, objectLoggerConfigBean, httpBean);
            fixedThreadPool.execute(sendLogForItemsTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
