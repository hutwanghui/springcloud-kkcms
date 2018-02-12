package com.kk.gate.handler.Oauth;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.common.util.JsonUtil;
import com.kk.common.util.http.HttpHelper;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.validate.vo.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败的处理器
 */
@Component
public class CustomOauthAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(CustomOauthAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("认证失败，失败的原因：" + e.getMessage());
        if (e instanceof ValidateCodeException) {
            ValidateCodeException validateCodeException = (ValidateCodeException) e;
            HttpHelper.setResponseJsonData(httpServletResponse, JSON.toJSONString(JsonUtil.getFailJsonObject(validateCodeException.getCode(), validateCodeException.getMessage())));
        } else {
            httpServletResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(JsonUtil.getFailJsonObject(e.getMessage())));
        }

    }
}
