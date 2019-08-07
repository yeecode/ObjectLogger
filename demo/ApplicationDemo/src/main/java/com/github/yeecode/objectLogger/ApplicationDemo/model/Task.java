package com.github.yeecode.objectlogger.ApplicationDemo.model;

import com.github.yeecode.objectlogger.client.annotation.LogTag;
import com.github.yeecode.objectlogger.client.handler.BuiltinTypeHandler;

public class Task {
    private Integer id;

    @LogTag
    private String taskName;

    @LogTag(alias = "UserId", extendedType = "userIdType")
    private int userId;

    @LogTag(alias = "Status")
    private String status;

    @LogTag(alias = "Description", builtinType = BuiltinTypeHandler.TEXT)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
