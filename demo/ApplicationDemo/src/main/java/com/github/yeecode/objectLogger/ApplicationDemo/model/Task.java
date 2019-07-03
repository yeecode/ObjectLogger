package com.github.yeecode.objectLogger.ApplicationDemo.model;

import com.github.yeecode.objectLogger.client.annotation.LogTag;
import com.github.yeecode.objectLogger.client.handler.BuiltinTypeHandler;

public class Task {
    private Integer id;

    @LogTag(name = "TaskName")
    private String taskName;

    @LogTag(name = "UserId", extendedType = "userIdType")
    private int userId;

    @LogTag(name = "Description", builtinType = BuiltinTypeHandler.TEXT)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}