package com.github.yeecode.objectlogger.client.task;

import com.github.yeecode.objectlogger.client.config.ObjectLoggerConfig;
import com.github.yeecode.objectlogger.client.http.HttpUtil;
import com.github.yeecode.objectlogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectlogger.client.model.OperationModel;
import com.google.gson.Gson;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class LogAttributesTask implements Runnable {
    private HttpUtil httpUtil;
    private String objectName;
    private String objectId;
    private String operator;
    private String operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private ObjectLoggerConfig objectLoggerConfig;

    private List<BaseAttributeModel> baseAttributeModelList;

    public LogAttributesTask(String objectName, String objectId, String operator, String operationName, String operationAlias,
                             String extraWords, String comment,
                             List<BaseAttributeModel> baseAttributeModelList, ObjectLoggerConfig objectLoggerConfig, HttpUtil httpUtil) {
        this.objectName = objectName;
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.baseAttributeModelList = baseAttributeModelList;
        this.objectLoggerConfig = objectLoggerConfig;
        this.httpUtil = httpUtil;
    }

    @Override
    public void run() {
        try {
            OperationModel operationModel = new OperationModel(objectLoggerConfig.getBusinessAppName(), objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, new Date());

            if (!CollectionUtils.isEmpty(baseAttributeModelList)) {
                operationModel.addBaseActionItemModelList(baseAttributeModelList);
            }
            httpUtil.sendLog(new Gson().toJson(operationModel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
