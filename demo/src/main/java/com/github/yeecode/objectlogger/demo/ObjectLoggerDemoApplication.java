package com.github.yeecode.objectlogger.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.objectlogger.demo", "com.github.yeecode.objectlogger"})
public class ObjectLoggerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectLoggerDemoApplication.class, args);
        System.out.println(
                "  ___  _     _           _   _                                \n" +
                " / _ \\| |__ (_) ___  ___| |_| |    ___   __ _  __ _  ___ _ __ \n" +
                "| | | | '_ \\| |/ _ \\/ __| __| |   / _ \\ / _` |/ _` |/ _ \\ '__|\n" +
                "| |_| | |_) | |  __/ (__| |_| |__| (_) | (_| | (_| |  __/ |   \n" +
                " \\___/|_.__// |\\___|\\___|\\__|_____\\___/ \\__, |\\__, |\\___|_|   \n" +
                "          |__/                          |___/ |___/           ");
        System.out.println("ObjectLogger Demo application start successfully!");
        System.out.println("Via following url to add logs:");
        System.out.println("###############################");
        System.out.println("http://127.0.0.1:12302/ObjectLoggerDemo/task/add");
        System.out.println("http://127.0.0.1:12302/ObjectLoggerDemo/task/start");
        System.out.println("http://127.0.0.1:12302/ObjectLoggerDemo/task/update");
        System.out.println("###############################");
    }
}
