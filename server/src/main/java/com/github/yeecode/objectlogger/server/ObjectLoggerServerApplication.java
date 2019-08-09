package com.github.yeecode.objectlogger.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.objectlogger"})
public class ObjectLoggerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectLoggerServerApplication.class, args);
    }

}
