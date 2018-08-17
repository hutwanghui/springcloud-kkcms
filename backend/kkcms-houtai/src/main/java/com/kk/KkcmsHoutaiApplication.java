package com.kk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableSwagger2
@MapperScan(basePackages = "com.kk.modules.*.dao")
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
public class KkcmsHoutaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkcmsHoutaiApplication.class, args);
	}
}
