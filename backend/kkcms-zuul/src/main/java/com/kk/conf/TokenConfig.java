package com.kk.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * Created by msi- on 2018/2/10.
 */
@Configuration
public class TokenConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "redisTokenStore")
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
