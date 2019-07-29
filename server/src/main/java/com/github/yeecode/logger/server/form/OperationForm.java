package com.github.yeecode.logger.server.form;

public class OperationForm {
    private String id;

    private String appName;

    private String objectName;

    private String objectId;

    private String operator;

    private String operationName;

    private String operationAlias;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
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

    public interface Query{}
}
