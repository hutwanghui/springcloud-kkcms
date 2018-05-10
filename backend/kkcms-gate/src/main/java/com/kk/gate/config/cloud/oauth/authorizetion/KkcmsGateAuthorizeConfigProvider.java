package com.kk.gate.config.cloud.oauth.authorizetion;

import com.kk.common.security.AuthorizeConfigProvider;
import com.kk.gate.config.properties.SecurityProperties;
import com.kk.gate.validate.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by msi- on 2018/4/5.
 */
@Component
@Order(Integer.MIN_VALUE)
public class KkcmsGateAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/",
                "/kkcms-user/user/register",
                "/hystrix.stream",
                "/swagger-ui.html",
                SecurityConstants.DEFAULT_OAUTH2_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                securityProperties.getBrowser().getLoginPage(),
                "/oauthcode/*",
                "/code/*",
                "/kkcms-houtai/hadoop/uploadShell",
                securityProperties.getBrowser().getSessionInvalidPage()).
                permitAll()
                .antMatchers("/hystrix/**").hasRole("ADMIN")
                .antMatchers("/**/session/**").authenticated();
    }
}
