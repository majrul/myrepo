package com.training.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.embedded.RedisServer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private DummyService dummyService;
	
	private static RedisServer redisServer;
	
	@BeforeClass
	public static void start() throws Exception {
		redisServer = new RedisServer(6789);
		redisServer.start();
	}
	
	@AfterClass
	public static void stop() {
		redisServer.stop();
	}
	
	@Test
	public void contextLoads() {
		System.out.println(dummyService.execute("dummy"));
		System.out.println(dummyService.execute("dummy"));
		System.out.println(dummyService.execute("dummy"));
	}

}
