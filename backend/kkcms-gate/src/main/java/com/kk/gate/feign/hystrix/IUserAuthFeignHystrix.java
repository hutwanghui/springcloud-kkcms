package com.kk.gate.feign.hystrix;

import com.kk.common.vo.KUserVo;
import com.kk.common.vo.PermissionInfo;
import com.kk.common.vo.SocialVo;
import com.kk.gate.feign.IUserAuthFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * Created by msi- on 2018/1/31.
 */
@Component
public class IUserAuthFeignHystrix implements IUserAuthFeign {
    private static Logger logger = LoggerFactory.getLogger(IUserAuthFeignHystrix.class);

    @Override
    public KUserVo getByUsername(@PathVariable("username") String username) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return new KUserVo("UNKOWN", "UNKOWN", null);
    }

    @Override
    public KUserVo getByMobile(@PathVariable("mobile") String mobile) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return new KUserVo("UNKOWN", "UNKOWN", null);
    }

    @Override
    public SocialVo getByUserId(@PathVariable("userId") String userId) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return new SocialVo();
    }

    @Override
    public Set<PermissionInfo> findPermissionInfoByRoles(@PathVariable("roles") String roles) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return null;
    }
}
