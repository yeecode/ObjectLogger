package com.github.yeecode.objectlogger.ApplicationDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.objectlogger.ApplicationDemo", "com.github.yeecode.objectlogger"})
public class ApplicationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoApplication.class, args);
    }
}
