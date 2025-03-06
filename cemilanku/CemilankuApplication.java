package com.cemilanku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CemilankuApplication {
	public static void main(String[] args) {
		SpringApplication.run(CemilankuApplication.class, args);
	}
}
