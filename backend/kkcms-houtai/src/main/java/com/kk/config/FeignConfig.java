package com.kk.config;

import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by msi- on 2018/5/10.
 */
@Configuration
public class FeignConfig {
    @Bean
    Retryer feignRetryer() {
        return new Retryer() {
            @Override
            public void continueOrPropagate(RetryableException e) {
                throw e;
            }

            @Override
            public Retryer clone() {
                return this;
            }
        };
    }
}
