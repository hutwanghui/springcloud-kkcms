package com.kk.modules.attendence.feign.hystrix;

import com.kk.common.utils.PageUtils;
import com.kk.modules.attendence.feign.IAtdComplatedFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
public class IAtdComplatedFeignHystrix implements IAtdComplatedFeign {
    private static Logger logger = LoggerFactory.getLogger(IAtdComplatedFeignHystrix.class);

    @Override
    public PageUtils queryPage_complated(Map<String, Object> params) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return null;
    }
}
