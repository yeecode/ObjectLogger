package com.github.yeecode.logger.client.model;

public class AttributeModel extends BaseAttributeModel {
    private Integer id;
    private Integer operationId;

    public AttributeModel() {
    }

    public AttributeModel(BaseAttributeModel baseAttributeModel) {
        this.setAttributeType(baseAttributeModel.getAttributeType());
        this.setAttributeName(baseAttributeModel.getAttributeName());
        this.setAttributeAlias(baseAttributeModel.getAttributeAlias());
        this.setOldValue(baseAttributeModel.getOldValue());
        this.setNewValue(baseAttributeModel.getNewValue());
        this.setDiffValue(baseAttributeModel.getDiffValue());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }
}
