package com.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by msi- on 2018/1/31.
 * 已经测试通过，通过引入来配置Feign响应的日志格式
 * NONE:不记录
 * BASIC:仅记录请求方法、URL以及响应状态码和执行时间
 * HEADERS:BASIC+记录请求和响应头信息
 * FULL：记录所有请求和响应的明细
 */
@Configuration
public class FullLogFeignConfig {
    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

   /* @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin", "admin123456");
    }*/
}
