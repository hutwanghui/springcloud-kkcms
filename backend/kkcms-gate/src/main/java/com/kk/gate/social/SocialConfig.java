package com.kk.gate.social;

import com.kk.gate.config.properties.SocialProperties;
import com.kk.gate.social.qq.KkcmsSocialSecurityConfig;
import com.kk.gate.social.qq.MyConnectionSignUp;
import com.kk.gate.validate.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by msi- on 2018/1/27.
 * 社交主类配置类
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SocialProperties socialProperties;

    //避免用户手动注册和绑定，系统帮忙注册
    @Autowired(required = false)
    private MyConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("sys_");
        //自动注册注入
        if (connectionSignUp != null) {
            //此时自动插入的userId就是connection的getDisplayName()，可以自行根据业务来改
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配类
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer kkcmsSocialSecurityConfig() {
        String filterProcessesUrl = socialProperties.getFilterProcessesUrl();
        KkcmsSocialSecurityConfig configurer = new KkcmsSocialSecurityConfig(filterProcessesUrl);
        return configurer;
    }

    /**
     * 处理注册流程的工具类
     * 在注册过程中拿到springsocial中的信息
     * 注册完成如何把业务user传入springsocial
     * @param factoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator) {
        return new ProviderSignInUtils(factoryLocator, getUsersConnectionRepository(factoryLocator));
    }

}

