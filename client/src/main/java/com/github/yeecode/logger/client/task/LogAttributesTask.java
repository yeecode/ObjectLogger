package com.github.yeecode.logger.client.task;

import com.github.yeecode.logger.client.config.ObjectLoggerConfig;
import com.github.yeecode.logger.client.http.HttpUtil;
import com.github.yeecode.logger.client.http.JacksonUtils;
import com.github.yeecode.logger.client.model.BaseAttributeModel;
import com.github.yeecode.logger.client.model.OperationModel;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class LogAttributesTask implements Runnable {
    private String objectName;
    private Integer objectId;
    private String operator;
    private String operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private ObjectLoggerConfig objectLoggerConfig;

    private List<BaseAttributeModel> baseAttributeModelList;

    public LogAttributesTask(String objectName, Integer objectId, String operator, String operationName, String operationAlias,
                             String extraWords, String comment,
                             List<BaseAttributeModel> baseAttributeModelList, ObjectLoggerConfig objectLoggerConfig) {
        this.objectName = objectName;
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.baseAttributeModelList = baseAttributeModelList;
        this.objectLoggerConfig = objectLoggerConfig;
    }

    @Override
    public void run() {
        try {
            OperationModel operationModel = new OperationModel(objectLoggerConfig.getBusinessAppName(), objectName, objectId, operator,
                    operationName, operationAlias, extraWords, comment, new Date());

            if (!CollectionUtils.isEmpty(baseAttributeModelList)) {
                operationModel.addBaseActionItemModelList(baseAttributeModelList);
            }
            HttpUtil.sendLog(JacksonUtils.toJSONString(operationModel), objectLoggerConfig.getServerAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
