package com.kk.modules.attendence.feign;

import com.kk.common.utils.PageUtils;
import com.kk.config.FullLogFeignConfig;
import com.kk.modules.attendence.feign.hystrix.IAtdComplatedFeignHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@FeignClient(name = "kkcms-wx",fallback = IAtdComplatedFeignHystrix.class, configuration = FullLogFeignConfig.class)
public interface IAtdComplatedFeign {
    @RequestMapping(value = "/attendFegin/atd/complated/query", method = RequestMethod.GET)
    public PageUtils queryPage_complated(@RequestParam Map<String, Object> params);
}
