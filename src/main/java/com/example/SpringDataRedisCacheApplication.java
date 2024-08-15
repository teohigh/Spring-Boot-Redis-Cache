package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Cho phép sử dụng cache bằng cách sử dụng @Cacheable, @CachePut, @CacheEvict
public class SpringDataRedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisCacheApplication.class, args);
	}

}
