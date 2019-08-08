package com.github.yeecode.objectlogger.ApplicationDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.objectlogger.ApplicationDemo", "com.github.yeecode.objectlogger"})
public class ApplicationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoApplication.class, args);
        System.out.println("Via following url to add logs:");
        System.out.println("###############################");
        System.out.println("http://127.0.0.1:8081/task/add");
        System.out.println("http://127.0.0.1:8081/task/start");
        System.out.println("http://127.0.0.1:8081/task/update");
        System.out.println("###############################");
    }
}
