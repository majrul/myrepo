package com.training.demo;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

	@Cacheable("dummy-region")
	public String execute(String arg) {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		
		return UUID.randomUUID().toString();
	}
}
