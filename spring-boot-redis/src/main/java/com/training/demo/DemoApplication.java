package com.training.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisServer;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

	/*@Bean
	public CacheManager cacheManager(StringRedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}*/
	
	/*@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration("localhost",6379);
		return new LettuceConnectionFactory(redisConf);
	}*/
	
	public static void main(String[] args) throws Exception {
		//new RedisServer(9876).start();
		SpringApplication.run(DemoApplication.class, args);
	}
}
