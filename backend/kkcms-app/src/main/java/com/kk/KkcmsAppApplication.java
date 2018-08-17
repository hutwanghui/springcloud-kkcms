package com.kk;

import com.kk.netty.server.TCPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
public class KkcmsAppApplication {

    @Autowired
    private TCPServer tcpServer;

    public static void main(String[] args) {
        SpringApplication.run(KkcmsAppApplication.class, args);
    }
}
