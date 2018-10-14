package com.training.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages="com.training.demo.repository")
//@EntityScan(basePackages="com.training.demo.entity")
//@ComponentScan(basePackages="com.training.demo.controller")
public class SpringBootDataRepositoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataRepositoriesApplication.class, args);
	}
}
