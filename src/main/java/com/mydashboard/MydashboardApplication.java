package com.mydashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MydashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydashboardApplication.class, args);
	}

}
