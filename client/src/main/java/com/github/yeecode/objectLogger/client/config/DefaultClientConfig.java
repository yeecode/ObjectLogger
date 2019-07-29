package com.github.yeecode.objectLogger.client.config;

import com.github.yeecode.objectLogger.client.handler.BaseExtendedTypeHandler;
import com.github.yeecode.objectLogger.client.service.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaotianzeng
 * @version V1.0
 * @date 2019-07-29 13:41
 */
@Configuration
public class DefaultClientConfig {
    @Autowired
    private ObjectLoggerConfig objectLoggerConfig;

    @Autowired(required = false)
    private BaseExtendedTypeHandler baseExtendedTypeHandler;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Bean
    public LogClient client() {
        return new LogClient(objectLoggerConfig, baseExtendedTypeHandler, fixedThreadPool);
    }
}