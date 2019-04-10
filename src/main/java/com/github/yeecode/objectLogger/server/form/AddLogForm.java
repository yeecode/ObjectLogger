package com.github.yeecode.objectLogger.server.form;

public class AddLogForm {
    private String logJsonString;

    public String getLogJsonString() {
        return logJsonString;
    }

    public void setLogJsonString(String logJsonString) {
        this.logJsonString = logJsonString;
    }

    public interface AddLog {
    }
}