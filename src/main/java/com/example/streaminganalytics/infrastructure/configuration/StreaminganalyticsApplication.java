package com.example.streaminganalytics.infrastructure.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.streaminganalytics")
@EnableMongoRepositories(basePackages = "com.example.streaminganalytics.domain.repository")
public class StreaminganalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreaminganalyticsApplication.class, args);
	}

}
