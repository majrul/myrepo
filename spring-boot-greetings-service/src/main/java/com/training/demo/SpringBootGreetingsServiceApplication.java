package com.training.demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootGreetingsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootGreetingsServiceApplication.class, args);
	}
}

@RestController
class HelloController {
	
	@GetMapping("/hello")
	public Map<String, String> hello() {
		return Collections.singletonMap("message", "Hello World");
	}
}