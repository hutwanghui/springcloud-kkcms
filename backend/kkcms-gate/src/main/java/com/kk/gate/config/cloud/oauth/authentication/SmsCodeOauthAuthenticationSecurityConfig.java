package com.kk.gate.config.cloud.oauth.authentication;

import com.kk.gate.handler.CustomAuthenticationFailureHandler;
import com.kk.gate.handler.CustomAuthenticationSuccessHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationFailureHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationSuccessHandler;
import com.kk.gate.service.impl.UserDetailsServiceImpl;
import com.kk.gate.validate.code.sms.filter.SmsAuthenticationFilter;
import com.kk.gate.validate.code.sms.provider.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by msi- on 2018/1/26.
 * 配置自定义短信验证码拦截器、验证器进入拦截器链里
 */
@Configuration
public class SmsCodeOauthAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomOauthAuthenticationFailureHandler customOauthAuthenticationFailureHandler;
    @Autowired
    private CustomOauthAuthenticationSuccessHandler customOauthAuthenticationSuccessHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(customOauthAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(customOauthAuthenticationFailureHandler);
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);
        //将认证过滤器加在UsernamePasswordAuthenticationFilter后面
        http.authenticationProvider(smsAuthenticationProvider).addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
