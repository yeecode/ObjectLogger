package com.github.yeecode.logger.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectLoggerConfig {
    @Value("${yeecode.objectLogger.businessAppName}")
    private String businessAppName;
    @Value("${yeecode.objectLogger.serverAddress}")
    private String serverAddress;
    @Value("${yeecode.objectLogger.autoLogAttributes}")
    private String autoLogAttributes;

    public String getBusinessAppName() {
        return businessAppName;
    }

    public void setBusinessAppName(String businessAppName) {
        this.businessAppName = businessAppName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getAutoLogAttributes() {
        return autoLogAttributes;
    }

    public void setAutoLogAttributes(String autoLogAttributes) {
        this.autoLogAttributes = autoLogAttributes;
    }
}
