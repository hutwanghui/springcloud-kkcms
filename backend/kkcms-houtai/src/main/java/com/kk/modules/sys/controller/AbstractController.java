package com.kk.modules.sys.controller;

import com.kk.common.util.UserDetailsUtil;
import com.kk.common.utils.HttpContextUtils;
import com.kk.modules.sys.entity.SysUserEntity;
import com.kk.modules.sys.oauth2.UserInfo;
import com.kk.modules.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public SysUserEntity getUser(Object username) {
        return  userService.queryByUserName((String) username);
    }

    @Autowired
    private SysUserService userService;


    public Integer getUserId() throws UnsupportedEncodingException {
        return userService.queryByUserName(HttpContextUtils.getUser_name()).getId();
    }
}
