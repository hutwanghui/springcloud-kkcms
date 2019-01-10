package com.kk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hutwanghui on 2019/1/10 14:16.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc: 用于从yml配置文件中读取配置的配置读取类
 */

@Data
@ConfigurationProperties(prefix = "merryyou.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "merryyou";

    private OAuth2ClientProperties[] clients = {};
}
