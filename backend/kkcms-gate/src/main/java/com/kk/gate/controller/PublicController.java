package com.kk.gate.controller;


import com.kk.common.vo.HttpCallObj;
import com.kk.gate.config.SecurityPropertiesConfig;
import com.kk.gate.config.properties.SecurityProperties;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiangfei on 2017/10/16.
 */
@RestController
public class PublicController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    private static Logger logger = LoggerFactory.getLogger(PublicController.class);
    //把当前请求缓存到session里面，作为返回
    private RequestCache requestCache = new HttpSessionRequestCache();
    //spring的跳转工具
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping("/")
    public String login() {
        return "kkcms Gate success";
    }

    /*
    当需要身份验证的时候跳到这里抛出401错误
     */
    @RequestMapping(value = "/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ApiOperation(value = "html请求认证转发以及非html请求拦截")
    public HttpCallObj requireAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                //转发到登陆页面
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new HttpCallObj(HttpStatus.UNAUTHORIZED.value(), "访问的服务需要身份认证");
    }

    @GetMapping(value = "/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ApiOperation(value = "session超时")
    public HttpCallObj sessionInvalid() {
        return new HttpCallObj(HttpStatus.UNAUTHORIZED.value(), "Session失效");
    }
}
