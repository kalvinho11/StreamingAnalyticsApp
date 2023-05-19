package com.example.streaminganalytics.infrastructure.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StreaminganalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreaminganalyticsApplication.class, args);
	}

}
