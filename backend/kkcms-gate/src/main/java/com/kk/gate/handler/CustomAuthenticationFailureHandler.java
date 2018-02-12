package com.kk.gate.handler;

import com.alibaba.fastjson.JSON;

import com.kk.common.util.JsonUtil;
import com.kk.common.util.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败的处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("认证失败，失败的原因：" + e.getMessage());
        AuthenticationException ae = (AuthenticationException) httpServletRequest.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (ae == null) {
            HttpHelper.setResponseJsonData(httpServletResponse, JSON.toJSONString(JsonUtil.getFailJsonObject(e.getMessage())));
        } else {
            HttpHelper.setResponseJsonData(httpServletResponse, JSON.toJSONString(JsonUtil.getFailJsonObject(e.getMessage())));
        }

    }
}
