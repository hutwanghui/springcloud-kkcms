package com.kk.gate.config.cloud.Feign;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * Created by msi- on 2018/1/31.
 * 这是在开启全局熔断器的情况下指定某一服务关闭熔断器机制的配置类
 * 未通过测试：只需要在feignClient上加上configuration=DisableHystrixConfig.class即可
 */

public class DisableHystrixConfig {
    /*@Bean*/
   /* @Scope("prototype")*/
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}
