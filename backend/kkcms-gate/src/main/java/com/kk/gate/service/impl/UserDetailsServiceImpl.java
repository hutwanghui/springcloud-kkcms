package com.kk.gate.service.impl;

import com.kk.common.vo.SocialVo;
import com.kk.gate.exception.MyAuthenticationException;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.feign.IUserAuthFeign;
import com.kk.common.vo.KUserVo;
import com.kk.gate.vo.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/1/19.
 * 实现UserDetailsService接口，这样可以自定义认证方式，比如结合数据库
 * 实现SocialUserDetails接口，实现Social的认证数据库获取
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Autowired
    private IUserAuthFeign userAuthFeign;

    //重写loadUserByUsername 方法获得 userdetails 类型用户
    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        logger.info("kkcms进行用户认证:loadUserByUsername:{}", username);
        if (StringUtils.isEmpty(username)) {
            logger.info("用户名为空");
            throw new UsernameNotFoundException("用户名为空");
        }
        KUserVo kUserVo = userAuthFeign.getByUsername(username);
        if (kUserVo == null) {
            logger.info("用户名不存在");
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserInfo userInfo = new UserInfo(kUserVo);
        return userInfo;
    }

    public UserDetails loadUserByMobile(String mobile) {
        logger.info("kkcms进行短信认证：loadUserByMobile:{}", mobile);
        if (StringUtils.isEmpty(mobile)) {
            logger.info("手机号为空");
            throw new UsernameNotFoundException("手机号为空");
        }
        KUserVo kUserVo = userAuthFeign.getByMobile(mobile);
        if (kUserVo == null) {
            logger.info("账号不存在");
            throw new UsernameNotFoundException("账号不存在");
        }
        UserInfo userInfo = new UserInfo(kUserVo);
        return userInfo;
    }


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("kkcms进行社交用户认证:loadUserByUserId:{}", userId);
        if (StringUtils.isEmpty(userId)) {
            logger.info("用户ID为空");
            throw new UsernameNotFoundException("用户ID为空");
        }
        SocialVo socialVo = userAuthFeign.getByUserId(userId);
        if (socialVo == null) {
            logger.info("账号不存在");
            throw new UsernameNotFoundException("账号不存在");
        }
        return null;
    }
}
