package com.kk.gate.social.qq.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.gate.social.qq.api.QQ;
import com.kk.gate.social.qq.api.entity.QQUserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * Created by msi- on 2018/1/27.
 * 实现返回用户信息接口
 * AbstractOAuth2ApiBinding里面已经有俩个属性了
 * 这是一个多实例对象，因为每个人的token都是不一样的
 * accessToken：API是负责执行流程里的第六步的，所以需要这个接受前5步发送来的token，通过token才能获取用户信息
 * restTemplate：帮助发送http请求给第三方client
 * 实现了第六步，获取用户信息，并用QQUserInfo封装用户信息
 */
public class QQImp extends AbstractOAuth2ApiBinding implements QQ {

    private static Logger logger = LoggerFactory.getLogger(QQUserInfo.class);
    private String appId;
    private String openId;
    private ObjectMapper mapper = new ObjectMapper();

    //http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0
    private static final String QQ_URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    //http://wiki.connect.qq.com/get_user_info(access_token由父类提供)
    private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    public QQImp(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        //默认的父类构造方法是将token放在header（TokenStrategy.AUTHORIZATION_HEADER）里面，而QQ官方的是使用使用url传递，
        this.appId = appId;
        //替换%S
        String url = String.format(QQ_URL_GET_OPENID, accessToken);
        //使用restTemplate发送请求获取openId。参照官方请求的方式，返回的result应该是：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("【QQImpl】 QQ_URL_GET_OPENID={} result={}", QQ_URL_GET_OPENID, result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("【QQImpl】 QQ_URL_GET_USER_INFO={} result={}", QQ_URL_GET_USER_INFO, result);
        QQUserInfo userInfo = null;
        try {
            //将string字符串都城QQUserInfo。
            userInfo = mapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}