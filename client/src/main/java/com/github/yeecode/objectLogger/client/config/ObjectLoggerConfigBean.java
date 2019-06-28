package com.github.yeecode.objectLogger.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectLoggerConfigBean {
    @Value("${object.logger.appName}")
    private String appName;
    @Value("${object.logger.add.log.api}")
    private String addLogApi;
    @Value("${object.logger.autoLog}")
    private String autoLog;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAddLogApi() {
        return addLogApi;
    }

    public void setAddLogApi(String addLogApi) {
        this.addLogApi = addLogApi;
    }

    public String getAutoLog() {
        return autoLog;
    }

    public void setAutoLog(String autoLog) {
        this.autoLog = autoLog;
    }
}
