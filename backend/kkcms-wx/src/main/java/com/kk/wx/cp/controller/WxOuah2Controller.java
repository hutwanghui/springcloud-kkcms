/**
 * Copyright (c) 2010-2015 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2015湖南蚁坊软件有限公司。保留所有权利。
 */
package com.kk.wx.cp.controller;


import com.kk.wx.cp.entity.WxUser;
import com.kk.wx.cp.service.IWxUserService;
import com.kk.wx.cp.service.OAuth2Service;
import com.kk.wx.cp.service.impl.WxUserServiceImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * WxOuah2Controller
 *
 * @author liaozhidan
 * @date 2017/8/20
 */
@Controller
@RequestMapping("/wx")
public class WxOuah2Controller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxCpService wxService;


    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private WxUserServiceImpl userService;


    private String oAuth2Url(String corpid, String redirect_uri) {
        try {
            redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + corpid + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=snsapi_base&state=zzds#wechat_redirect";
        return oauth2Url;
    }

    /**
     * 跳转至auth2认证地址
     *
     * @param request
     * @param resultUrl
     * @return
     */
    @GetMapping("/auth2")
    public String Oauth2API(HttpServletRequest request, @RequestParam String resultUrl) {
        logger.info("进入oauth2认证部分");
        String CropId = this.wxService.getWxCpConfigStorage().getCorpId();
        String redirectUrl = "";
        if (resultUrl != null) {
            String serverName = request.getServerName();
            String backUrl = request.getScheme() + "://" + serverName + ":8966/wx/login?oauth2url=" + resultUrl;
            redirectUrl = oAuth2Url(CropId, backUrl);
        }
        logger.info("返回" + redirectUrl);
        return "redirect:" + redirectUrl;
    }


    /**
     * 跳转至二次验证地址
     *
     * @param request
     * @param code
     * @return
     */
    @GetMapping("/validate")
    public String validate(HttpServletRequest request, @RequestParam String code) throws WxErrorException {
        String[] userArray = oAuth2Service.getUserInfo(code);
        request.getSession().setAttribute("WX_USER_ID", userArray[0]);
        logger.info("开始微信登录: wxUserId:{} :", userArray[0]);
        WxUser adminEntity = userService.select_one_byWxId(userArray[0]);
        if (adminEntity != null) {
            logger.info("开始微信登录: wxUserId:{}:", userArray[0]);
            userService.authenticate(userArray[0]);
            return "";
        } else {
            return "";
        }
    }

    /**
     * 登录获取用户信息
     *
     * @param request
     * @param code
     * @param oauth2url
     * @return
     */
    @GetMapping("/login")
    public String Oauth2MeUrl(HttpServletRequest request, @RequestParam String code, @RequestParam String oauth2url) {
        logger.info("使用code进行登陆");
        try {
            String[] userArray = oAuth2Service.getUserInfo(code);
            request.getSession().setAttribute("WX_USER_ID", userArray[0]);
            request.getSession().setAttribute("WX_USER_NAME", userArray[1]);
        } catch (WxErrorException e) {
            e.printStackTrace();
            logger.error("使用用户code登录失败！code: " + code);
            throw new RuntimeException("使用用户code登录失败");
        }

        return "redirect:" + oauth2url;
    }
}
