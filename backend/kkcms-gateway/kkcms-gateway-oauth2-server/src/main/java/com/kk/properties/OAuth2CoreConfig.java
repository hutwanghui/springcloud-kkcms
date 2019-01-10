package com.kk.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hutwanghui on 2019/1/10 14:15.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc: 用于将Oauth2的属性进行装配的配置类
 */

@Configuration
@EnableConfigurationProperties(OAuth2Properties.class)
public class OAuth2CoreConfig {
}
