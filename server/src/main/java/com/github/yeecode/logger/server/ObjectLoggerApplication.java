package com.github.yeecode.logger.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.github.yeecode.logger"})
public class ObjectLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjectLoggerApplication.class, args);
	}

}
