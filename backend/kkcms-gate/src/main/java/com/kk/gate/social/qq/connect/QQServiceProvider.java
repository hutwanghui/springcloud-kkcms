package com.kk.gate.social.qq.connect;

import com.kk.gate.social.qq.api.QQ;
import com.kk.gate.social.qq.api.impl.QQImp;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by msi- on 2018/1/27.
 * 为1-5步骤提供API和Template
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        //前两个参数是在QQ互联上开发者注册的APPID和APPKEY，相当于APP的用户名和密码，每次向QQ发请求都需要带着这两个参数，QQ才能知道是哪个开发者的应用向他发请求
        //第三个参数对应流程中的第一步：将用户导向认证服务器的地址,由QQ提供
        //第四个参数对应流程中的第四步：申请令牌的地址，由QQ提供
        //OAuth2Template(clientId,clientSecret,autorizeUrl,accessTokenUrl)
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    @Override
    public QQ getApi(String accessToken) {
        //因为QQ里面的属性Token必须是多实例的，不能申明成spring组件，所以用new
        return new QQImp(accessToken, appId);
    }
}
