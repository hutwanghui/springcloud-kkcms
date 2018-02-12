package com.kk.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class KkcmsCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkcmsCenterApplication.class, args);
	}
}
