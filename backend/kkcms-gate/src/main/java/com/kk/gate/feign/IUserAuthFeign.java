package com.kk.gate.feign;

import com.kk.common.vo.KUserVo;
import com.kk.common.vo.PermissionInfo;
import com.kk.common.vo.SocialVo;
import com.kk.gate.config.cloud.Feign.BasicLogFeignConfig;
import com.kk.gate.config.cloud.Feign.FullLogFeignConfig;
import com.kk.gate.feign.hystrix.IUserAuthFeignHystrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * Created by msi- on 2018/1/19.
 *
 * @FeignClient用于通知Feign组件对该接口进行代理(不需要编写接口实现)
 */
@FeignClient(name = "kkcms-user", fallback = IUserAuthFeignHystrix.class, configuration = FullLogFeignConfig.class)
public interface IUserAuthFeign {
    /**
     * 认证
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "userDetailsFeign/user/username/{username}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "hyx-getByUsername")
    KUserVo getByUsername(@PathVariable("username") String username);

    @RequestMapping(value = "userDetailsFeign/user/mobile/{mobile}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "hyx-getByMobile")
    KUserVo getByMobile(@PathVariable("mobile") String mobile);

    @RequestMapping(value = "userDetailsFeign/social/qq/{userId}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "hyx-getByUserId")
    SocialVo getByUserId(@PathVariable("userId") String userId);

    /**
     * 通过角色名查询菜单
     *
     * @param roles 角色名称  分号隔开
     * @return 菜单列表
     */
    @RequestMapping(value = "userDetailsFeign/permissions/roles/{roles}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "hyx-findPermissionInfoByRoles")
    Set<PermissionInfo> findPermissionInfoByRoles(@PathVariable("roles") String roles);


}
