package com.kk.gate.config.cloud.oauth;

import com.kk.gate.config.SmsCodeAuthenticationSecurityConfig;
import com.kk.gate.config.cloud.oauth.authentication.SmsCodeOauthAuthenticationSecurityConfig;
import com.kk.gate.config.properties.SecurityProperties;
import com.kk.gate.config.properties.ValidateProperties;
import com.kk.gate.handler.CustomAuthenticationFailureHandler;
import com.kk.gate.handler.CustomAuthenticationSuccessHandler;
import com.kk.gate.handler.CustomLogoutSuccessHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationFailureHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationSuccessHandler;
import com.kk.gate.service.impl.UserDetailsServiceImpl;
import com.kk.gate.session.KkcmsExpiredSessionStrategy;
import com.kk.gate.session.KkcmsInvalidSessionStaregy;
import com.kk.gate.validate.SecurityConstants;
import com.kk.gate.validate.UnifyValiteCoderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Created by msi- on 2018/2/2.
 * app安全配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private CustomOauthAuthenticationSuccessHandler customOauthAuthenticationSuccessHandler;
    @Autowired
    private CustomOauthAuthenticationFailureHandler customOauthAuthenticationFailureHandler;
    @Autowired
    private UnifyValiteCoderFilter unifyValiteCoderFilter;
    @Autowired
    private SmsCodeOauthAuthenticationSecurityConfig smsCodeOauthAuthenticationSecurityConfig;
    @Autowired(required = false)
    private TokenStore redisTokenStore;
    @Autowired(required = false)
    private TokenStore jwtTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if (jwtTokenStore != null) {
            resources.tokenStore(jwtTokenStore);
        } else if (redisTokenStore != null) {
            resources.tokenStore(redisTokenStore);
        }
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .apply(smsCodeOauthAuthenticationSecurityConfig)
                .and()
                .addFilterBefore(unifyValiteCoderFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .formLogin().loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(customOauthAuthenticationSuccessHandler)
                .failureHandler(customOauthAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/kkcms-user/user/register","/swagger-ui.html", SecurityConstants.DEFAULT_OAUTH2_UNAUTHENTICATION_URL, SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, securityProperties.getBrowser().getLoginPage(), "/oauthcode/*", "/code/*", securityProperties.getBrowser().getSessionInvalidPage()).permitAll()
                .antMatchers("/hystrix/**", "/hystrix.stream").hasRole("ADMIN")
                .antMatchers("/**/session/**").authenticated()
                .anyRequest()/*.access("@permissionService.hasPermission(request,authentication)")*/.authenticated()
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public KkcmsInvalidSessionStaregy kkcmsInvalidSessionStaregy() {
        KkcmsInvalidSessionStaregy kkcmsInvalidSessionStaregy = new KkcmsInvalidSessionStaregy(securityProperties.getBrowser().getSessionInvalidPage());
        return kkcmsInvalidSessionStaregy;
    }

    @Bean
    public KkcmsExpiredSessionStrategy kkcmsExpiredSessionStrategy() {
        KkcmsExpiredSessionStrategy kkcmsExpiredSessionStrategy = new KkcmsExpiredSessionStrategy(securityProperties.getBrowser().getSessionInvalidPage());
        return kkcmsExpiredSessionStrategy;
    }

}
