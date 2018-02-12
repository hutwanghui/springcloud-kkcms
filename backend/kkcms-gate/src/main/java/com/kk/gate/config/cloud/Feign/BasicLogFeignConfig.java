package com.kk.gate.config.cloud.Feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by msi- on 2018/1/31.
 */
@Configuration
public class BasicLogFeignConfig {
    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
