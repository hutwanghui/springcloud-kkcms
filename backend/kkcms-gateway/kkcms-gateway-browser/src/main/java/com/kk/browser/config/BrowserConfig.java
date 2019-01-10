package com.kk.browser.config;


import com.kk.browser.handler.MyAuthenticationFailureHandler;
import com.kk.browser.handler.MyAuthenticationLogoutHandler;
import com.kk.browser.handler.MyAuthenticationSuccessHandler;
import com.kk.browser.service.MyUserDetailsService;
import com.kk.browser.strategy.MySessionInformationExpiredStrategy;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;

/**
 * 处理用户信息获取逻辑 使用 UserDetailsService
 * 处理用户校验逻辑 使用 UserDetails
 * 处理密码加密解密 使用 PasswordEncoder
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)// 控制权限注解
public class BrowserConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private MyAuthenticationLogoutHandler myAuthenticationLogoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户认证部分
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 忽略静态资源的拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/img/**", "/css/**", "/images/**", "fav.ico");
    }

    /**
     * 请求授权部分
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 使用表单登陆
         */
        http

                .sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(new MySessionInformationExpiredStrategy())
                .and()
                .invalidSessionUrl("/session/invaild")
                .and()
                .formLogin()
                .loginPage("/hut-signIn.html")
                .loginProcessingUrl("/authentication/form")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/hut-signIn.html", "/session/invaild", "/authentication/require", "/auth/qq").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .logout().logoutSuccessHandler(myAuthenticationLogoutHandler)
        ;
    }


}
