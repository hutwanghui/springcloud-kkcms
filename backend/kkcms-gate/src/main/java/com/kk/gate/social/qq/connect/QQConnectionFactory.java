package com.kk.gate.social.qq.connect;

import com.kk.gate.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * Created by msi- on 2018/1/27.
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * 传入前面自定义的组件和配置商ID
     *
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
