package com.example.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.example.spring.batch.controller","com.example.spring.batch.config","com.example.spring.batch.config"
	,"com.example.spring.batch.model1","com.example.spring.batch.processor"})
public class SpringBatchJsonToXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchJsonToXmlApplication.class, args);
	}

}
