package com.kk.gate.config.cloud.oauth;


import com.kk.gate.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * EnableAuthorizationServer即可实现了认证服务器，使用spring oauth提供的四种授权方式
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;//

    @Autowired
    private UserDetailsServiceImpl userDetailsService;//自定义用户服务

    //因为配置的时候是用@ConditionalOnProperty
    @Autowired(required = false)
    private TokenStore redisTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired(required = false)
    private TokenEnhancer jwttokenEnhancer;


    /**
     * 针对端口入口点，如何进行身份认证
     * 处理/oauth/token请求的入口点
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
  /*      if (jwtTokenStore != null) {
            endpoints.tokenStore(jwtTokenStore).authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService); // 支持
            // password
            // grant
            // type;
        } else*/
        if (redisTokenStore != null) {
            if (jwttokenEnhancer != null) {
                TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
                List<TokenEnhancer> enhancers = new ArrayList<>();
                enhancers.add(jwttokenEnhancer);
                enhancers.add(jwtAccessTokenConverter);
                enhancerChain.setTokenEnhancers(enhancers);
                endpoints.tokenEnhancer(enhancerChain).tokenStore(redisTokenStore).authenticationManager(authenticationManager)
                        .userDetailsService(userDetailsService); // 支持
            } else {
                endpoints.tokenStore(redisTokenStore).authenticationManager(authenticationManager)
                        .userDetailsService(userDetailsService); // 支持
            }

            // password
            // grant
            // type;
        }

        if (jwtAccessTokenConverter != null) {
            endpoints.tokenStore(redisTokenStore).authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService).accessTokenConverter(jwtAccessTokenConverter); // 支持
        }

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
        ;
    }

    /**
     * 指定给哪些第三方应用发令牌，以及应用的配置是什么
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("kkcms-client")
                .secret("kkcms-secret")
                .accessTokenValiditySeconds(7200)//令牌有效时间，秒为单位
                .scopes("all", "read", "write") //表示发出去的权限，请求中的scopes需要在这个里面
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")//支持的授权模式（5种方式）
                .and();
    }


}
