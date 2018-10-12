package com.training.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTrialApplication {

	public static void main(String[] args) {
		System.out.println("Loading Spring Boot..");
		SpringApplication.run(SpringBootTrialApplication.class, args);
	}
}
