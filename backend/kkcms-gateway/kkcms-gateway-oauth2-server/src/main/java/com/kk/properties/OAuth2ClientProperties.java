package com.kk.properties;

import lombok.Data;

/**
 * Created by hutwanghui on 2019/1/10 14:15.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc: 配置oauth2客户端的属性
 */

@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private Integer accessTokenValiditySeconds = 7200;
    private Integer refreshTokenValiditySecondsSeconds; // token刷新

    private String[] authorizedGrantTypes = {};
    private String[] redirectUris = {}; // 信任的回调域
    private String[] scopes = {};
}
