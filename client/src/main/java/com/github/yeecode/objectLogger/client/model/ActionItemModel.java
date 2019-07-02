package com.github.yeecode.objectLogger.client.model;

public class ActionItemModel extends BaseActionItemModel {
    private Integer id;
    private Integer actionId;

    public ActionItemModel() {
    }

    public ActionItemModel(BaseActionItemModel baseActionItemModel) {
        this.setAttributeType(baseActionItemModel.getAttributeType());
        this.setAttribute(baseActionItemModel.getAttribute());
        this.setAttributeName(baseActionItemModel.getAttributeName());
        this.setOldValue(baseActionItemModel.getOldValue());
        this.setNewValue(baseActionItemModel.getNewValue());
        this.setDiffValue(baseActionItemModel.getDiffValue());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}
