package com.github.yeecode.objectLogger.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionModel {
    private Integer id;
    private String appName;
    private String objectName;
    private Integer objectId;
    private String actor;
    private String action;
    private String actionName;
    private String extraWords;
    private String comment;
    private Date actionTime;
    private List<ActionItemModel> actionItemModelList = new ArrayList<ActionItemModel>();

    public ActionModel() {
    }

    public ActionModel(String appName, String objectName, Integer objectId, String actor,
                       String action, String actionName, String extraWords,
                       String comment, Date actionTime) {
        this.appName = appName;
        this.objectName = objectName;
        this.objectId = objectId;
        this.actor = actor;
        this.action = action;
        this.actionName = actionName;
        this.extraWords = extraWords;
        this.comment = comment;
        this.actionTime = actionTime;
    }

    public void addBaseActionItemModel(BaseActionItemModel baseActionItemModel){
        ActionItemModel actionItemModel = new ActionItemModel(baseActionItemModel);
        this.getActionItemModelList().add(actionItemModel);
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

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public List<ActionItemModel> getActionItemModelList() {
        return actionItemModelList;
    }

    public void setActionItemModelList(List<ActionItemModel> actionItemModelList) {
        this.actionItemModelList = actionItemModelList;
    }
}
