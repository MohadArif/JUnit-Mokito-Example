package com.example.projectoptimizewithredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProjectoptimizewithredisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectoptimizewithredisApplication.class, args);
	}

}
