package com.github.yeecode.objectLogger.ApplicationDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.objectLogger.ApplicationDemo", "com.github.yeecode.objectLogger"})
public class ApplicationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoApplication.class, args);
    }
}
