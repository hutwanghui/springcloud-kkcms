package com.kk.gate.feign.hystrix;

import com.kk.common.vo.KUserVo;
import com.kk.gate.feign.IApiAuthServerFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Created by msi- on 2018/2/9.
 */
@Component
public class IApiAuthServerFeignHystrix implements IApiAuthServerFeign {
    private static Logger logger = LoggerFactory.getLogger(IApiAuthServerFeignHystrix.class);

    @Override
    public ResponseEntity<Object> query(String query) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return new ResponseEntity<Object>("熔断器触发降级处理", HttpStatus.BAD_GATEWAY);
    }
}
