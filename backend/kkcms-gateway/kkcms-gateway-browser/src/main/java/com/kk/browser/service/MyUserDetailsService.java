package com.kk.browser.service;

import com.kk.browser.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by hutwanghui on 2019/1/8.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 可以从任何地方获取数据
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //写死一个admin
        logger.info("登录用户名:{}", username);
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码{}", password);
        return new UserInfo(username, password, Arrays.asList("admin","user","boss"));
    }
}
