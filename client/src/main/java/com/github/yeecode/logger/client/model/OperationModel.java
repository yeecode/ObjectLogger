package com.github.yeecode.logger.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationModel {
    private Integer id;
    private String appName;
    private String objectName;
    private Integer objectId;
    private String operator;
    private String operationName;
    private String operationAlias;
    private String extraWords;
    private String comment;
    private Date operationTime;
    private List<AttributeModel> attributeModelList = new ArrayList<AttributeModel>();

    public OperationModel() {
    }

    public OperationModel(String appName, String objectName, Integer objectId, String operator,
                          String operationName, String operationAlias, String extraWords,
                          String comment, Date operationTime) {
        this.appName = appName;
        this.objectName = objectName;
        this.objectId = objectId;
        this.operator = operator;
        this.operationName = operationName;
        this.operationAlias = operationAlias;
        this.extraWords = extraWords;
        this.comment = comment;
        this.operationTime = operationTime;
    }

    public void addBaseActionItemModelList(List<BaseAttributeModel> baseAttributeModelList) {
        for (BaseAttributeModel baseAttributeModel : baseAttributeModelList) {
            addBaseActionItemModel(baseAttributeModel);
        }
    }

    public void addBaseActionItemModel(BaseAttributeModel baseAttributeModel) {
        AttributeModel attributeModel = new AttributeModel(baseAttributeModel);
        this.getAttributeModelList().add(attributeModel);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationAlias() {
        return operationAlias;
    }

    public void setOperationAlias(String operationAlias) {
        this.operationAlias = operationAlias;
    }

    public String getExtraWords() {
        return extraWords;
    }

    public void setExtraWords(String extraWords) {
        this.extraWords = extraWords;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public List<AttributeModel> getAttributeModelList() {
        return attributeModelList;
    }

    public void setAttributeModelList(List<AttributeModel> attributeModelList) {
        this.attributeModelList = attributeModelList;
    }
}
