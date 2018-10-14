package com.training.demo;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.stereotype.Component;

@Component
public class RedisDemo implements CommandLineRunner {

	 @Autowired
	 private StringRedisTemplate redisTemplate;

	@Override
	public void run(String... args) throws Exception {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		Arrays.asList(1, 2, 3, 6).forEach((i) -> ops.increment("counter", i));
		System.out.println(ops.get("counter"));
		
		Map<String, String> map = new DefaultRedisMap<>("data", redisTemplate);
		map.put("spring", "boot");
		
		map = new DefaultRedisMap<>("data", redisTemplate);
		System.out.println(map.get("spring")); 
	}
	
}
