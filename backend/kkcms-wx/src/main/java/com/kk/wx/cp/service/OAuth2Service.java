/**
 * Copyright (c) 2010-2015 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2015湖南蚁坊软件有限公司。保留所有权利。
 */
package com.kk.wx.cp.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpOAuth2Service;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OAuth2Service
 */
@Service
public class OAuth2Service implements WxCpOAuth2Service {

    @Autowired
    private WxCpService wxService;

    @Override
    public String buildAuthorizationUrl(String state) {
        return wxService.getOauth2Service().buildAuthorizationUrl(state);
    }

    @Override
    public String buildAuthorizationUrl(String redirectUri, String state) {
        return wxService.getOauth2Service().buildAuthorizationUrl(redirectUri, state);
    }

    @Override
    public String[] getUserInfo(String code) throws WxErrorException {
        return wxService.getOauth2Service().getUserInfo(code);
    }

    @Override
    public String[] getUserInfo(Integer agentId, String code) throws WxErrorException {
        return wxService.getOauth2Service().getUserInfo(agentId, code);
    }
}
