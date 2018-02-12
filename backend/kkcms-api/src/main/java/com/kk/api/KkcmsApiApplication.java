package com.kk.api;

import com.kk.api.resolver.Scalar.GraphQLDate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.kk.api.mapper")
public class KkcmsApiApplication {
/*    @Bean
    public MomentItemResolver momentItemResolver() {
        return new MomentItemResolver();
    }*/

    public static void main(String[] args) {
        SpringApplication.run(KkcmsApiApplication.class, args);
    }
}
