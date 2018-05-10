//package com.kk.gate.config;
//
//import com.kk.gate.config.properties.SecurityProperties;
//import com.kk.gate.config.properties.ValidateProperties;
//import com.kk.gate.session.KkcmsExpiredSessionStrategy;
//import com.kk.gate.session.KkcmsInvalidSessionStaregy;
//import com.kk.gate.validate.SecurityConstants;
//import com.kk.gate.validate.UnifyValiteCoderFilter;
//import com.kk.gate.handler.CustomAuthenticationFailureHandler;
//import com.kk.gate.handler.CustomAuthenticationSuccessHandler;
//import com.kk.gate.handler.CustomLogoutSuccessHandler;
//import com.kk.gate.service.impl.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//import org.springframework.security.web.session.SessionInformationExpiredStrategy;
//import org.springframework.social.connect.web.SessionStrategy;
//
///**
// * Created by msi- on 2018/1/19.
// * 若想开启pc端只需打开注解，关闭OauthSecurityConfig的
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)// 控制权限注解
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;//自定义用户服务
//    @Autowired
//    private SecurityProperties securityProperties;
//    @Autowired
//    private ValidateProperties validateProperties;
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//    @Autowired
//    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//    @Autowired
//    private UnifyValiteCoderFilter unifyValiteCoderFilter;
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//
//    /**
//     * 用户认证部分
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //security 的BCryptPasswordEncoder强哈希方法来加密密码。
//        //auth.userDetailsService是既可以使用内存中的用户认证，也可以进行持久层的用户认证
//  /*      auth.authenticationProvider(provider);*/
//        auth.userDetailsService(userDetailsService)/*.passwordEncoder(passwordEncoder())*/;
//    }
//
//    /**
//     * 忽略静态资源的拦截
//     *
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/js/**", "/img/**", "/css/**", "/images/**", "fav.ico");
//    }
//
//    /**
//     * 请求授权部分
//     * antMatchers：使用Ant风格的路径配置
//     * regexMatchers:使用正则表达式风格的路径配置
//     * anyRequest：匹配所有请求路径
//     * 通过 authorizeRequests() 开启请求权限设置
//     * 通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
//     * 这个formLogin().permitAll() 方法允许基于表单登录的所有URL的所有用户的访问。
//     * Java配置使用and()方法相当于XML标签的关闭
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //解决Refused to display 'http://......' in a frame because it set 'X-Frame-Options' to 'DENY'. "错误
//        http.headers().frameOptions().disable();
//        //都可以访问的permitAll()
//        //登陆即可查看session信息，authenticated()
//        //其他页面的访问均需要验证权限（登陆，且权限也有区分）
//        http
//                .csrf().disable()
//                .apply(smsCodeAuthenticationSecurityConfig)
//                .and()
//                .addFilterBefore(unifyValiteCoderFilter, AbstractPreAuthenticatedProcessingFilter.class)
//                .formLogin().loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
//                .successHandler(customAuthenticationSuccessHandler)
//                .failureHandler(customAuthenticationFailureHandler)
//                .and()
//                .sessionManagement()
//                .invalidSessionStrategy(kkcmsInvalidSessionStaregy())
//                .maximumSessions(securityProperties.getBrowser().getMaximumSessions())
//                //T人下线用false，阻止后一个人登陆用true
//                .maxSessionsPreventsLogin(securityProperties.getBrowser().isPreventsLogin())
//                .expiredSessionStrategy(kkcmsExpiredSessionStrategy())
//                .and()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/kkcms-user/user/register", SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, securityProperties.getBrowser().getLoginPage(), "/code/*", securityProperties.getBrowser().getSessionInvalidPage()).permitAll()
//                .antMatchers("/**/session/**").authenticated()
//                .anyRequest()/*.access("@permissionService.hasPermission(request,authentication)")*/.authenticated()
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
//
//    }
//
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return new CustomLogoutSuccessHandler();
//    }
//
//    @Bean
//    public KkcmsInvalidSessionStaregy kkcmsInvalidSessionStaregy() {
//        KkcmsInvalidSessionStaregy kkcmsInvalidSessionStaregy = new KkcmsInvalidSessionStaregy(securityProperties.getBrowser().getSessionInvalidPage());
//        return kkcmsInvalidSessionStaregy;
//    }
//
//    @Bean
//    public KkcmsExpiredSessionStrategy kkcmsExpiredSessionStrategy() {
//        KkcmsExpiredSessionStrategy kkcmsExpiredSessionStrategy = new KkcmsExpiredSessionStrategy(securityProperties.getBrowser().getSessionInvalidPage());
//        return kkcmsExpiredSessionStrategy;
//    }
//
//    /**
//     * 此方法解决得了usernameNotFound异常抛出只能显示坏的凭证的问题
//     *
//     * @return
//     */
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setHideUserNotFoundExceptions(false);
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }
//
//
//
//}
