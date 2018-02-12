package com.kk.gate.social.qq.config;

import com.kk.gate.config.properties.SocialProperties;
import com.kk.gate.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import javax.sql.DataSource;

/**
 * Created by msi- on 2018/1/27.
 * 针对QQ返回令牌的的操作
 */
@Configuration
@ConditionalOnProperty(prefix = "com.kk.social.qq", name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        String appId = socialProperties.getQq().getAppId();
        String appSerct = socialProperties.getQq().getAppSecret();
        String provider = socialProperties.getQq().getProviderId();
        return new QQConnectionFactory(provider, appId, appSerct);
    }


}
