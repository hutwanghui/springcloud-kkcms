package com.kk.gate.feign;

import com.kk.gate.config.cloud.Feign.BasicLogFeignConfig;
import com.kk.gate.config.cloud.Feign.FullLogFeignConfig;
import com.kk.gate.feign.hystrix.IApiAuthServerFeignHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by msi- on 2018/2/9.
 */
@FeignClient(name = "kkcms-api", fallback = IApiAuthServerFeignHystrix.class, configuration = FullLogFeignConfig.class)
public interface IApiAuthServerFeign {
    @PostMapping(value = "/rest/post/query")
    ResponseEntity<Object> query(@RequestBody String query);
}
