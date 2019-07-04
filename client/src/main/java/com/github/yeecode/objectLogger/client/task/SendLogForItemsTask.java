package com.github.yeecode.objectLogger.client.task;

import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfigBean;
import com.github.yeecode.objectLogger.client.http.HttpBean;
import com.github.yeecode.objectLogger.client.model.ActionModel;
import com.github.yeecode.objectLogger.client.model.BaseActionItemModel;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class SendLogForItemsTask implements Runnable {
    private HttpBean httpBean;
    private String objectName;
    private Integer objectId;
    private String actor;
    private String action;
    private String actionName;
    private String extraWords;
    private String comment;
    private ObjectLoggerConfigBean objectLoggerConfigBean;

    private List<BaseActionItemModel> baseActionItemModelList;

    public SendLogForItemsTask(String objectName, Integer objectId, String actor, String action, String actionName,
                               String extraWords, String comment,
                               List<BaseActionItemModel> baseActionItemModelList, ObjectLoggerConfigBean objectLoggerConfigBean, HttpBean httpBean) {
        this.objectName = objectName;
        this.objectId = objectId;
        this.actor = actor;
        this.action = action;
        this.actionName = actionName;
        this.extraWords = extraWords;
        this.comment = comment;
        this.baseActionItemModelList = baseActionItemModelList;
        this.objectLoggerConfigBean = objectLoggerConfigBean;
        this.httpBean = httpBean;
    }

    @Override
    public void run() {
        try {
            ActionModel actionModel = new ActionModel(objectLoggerConfigBean.getAppName(), objectName, objectId, actor,
                    action, actionName, extraWords, comment, new Date());

            if (!CollectionUtils.isEmpty(baseActionItemModelList)) {
                actionModel.addBaseActionItemModelList(baseActionItemModelList);
            }
            httpBean.sendLog(JSON.toJSONString(actionModel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
