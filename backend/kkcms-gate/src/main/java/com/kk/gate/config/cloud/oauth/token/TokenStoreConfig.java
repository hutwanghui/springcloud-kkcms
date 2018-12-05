package com.kk.gate.config.cloud.oauth.token;

import com.kk.gate.config.cloud.oauth.token.jwt.JwtTokenEnhancer;
import com.kk.gate.config.cloud.oauth.token.store.RedisTemplateTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * redis存储token
 */
@Configuration
public class TokenStoreConfig {

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "jdbc", matchIfMissing = true)
    public TokenStore jdbcTokenStore() {

//		oauth_access_token oauth_refresh_token 创建两张表
//		return new JdbcTokenStore( dataSource ) ;
        return new JdbcTokenStore(dataSource);

    }

    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    public TokenStore redisTokenStore() {
//		return new RedisTokenStore( redisTemplate.getConnectionFactory() ) ; //单台redis服务器
        RedisTemplateTokenStore redisTemplateStore = new RedisTemplateTokenStore();
        redisTemplateStore.setRedisTemplate(redisTemplate);
        return redisTemplateStore;
    }

    //使用jwt替换原有的uuid生成token方式
    //prefix：检查配置项前缀，name：key名，havingValue：key对应的value值，表示当配置文件中的这个属性值等于value的时候加在这个注解的配置，否则不加载
    @Configuration
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "jwt", matchIfMissing = true)
    public static class JWTTokenConfig {
        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore tokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

        /**
         * jwt的特点之一：进行密钥签名
         * 不同应用的签名是不一样的，可以将这个密钥配置写在properties里面
         *
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey("hutwanghui");
            return accessTokenConverter;
        }

        /**
         * 为JWT令牌添加自定义信息的增强器
         *
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwttokenEnhancer() {
            return new JwtTokenEnhancer();
        }
    }


}
