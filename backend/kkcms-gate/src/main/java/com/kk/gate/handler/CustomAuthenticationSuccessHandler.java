package com.kk.gate.handler;

import com.alibaba.fastjson.JSON;

import com.kk.common.util.JsonUtil;
import com.kk.common.util.UserDetailsUtil;
import com.kk.common.util.http.HttpHelper;
import com.kk.common.vo.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 成功认证的处理器
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("web端认证登陆成功，用户是：" + authentication.getName());
        String result = JSON.toJSONString(JsonUtil.getSuccessJsonObject(true));
        if (authentication != null && authentication.getDetails() != null) {
            try {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                Map<String, Object> map = new HashMap<>();
                map.put("success", true);
                UserDetails userDetails = UserDetailsUtil.getCurrentUser();
                //map.put("userDetails", userDetails);
                logger.info("KKcms:userDetails:{}", userDetails);
                //如果是已经验证通过了的user
                if (userDetails != null) {
                    SessionInfo sessionInfo = new SessionInfo();
                    sessionInfo.setUsername(userDetails.getUsername());
                    httpServletRequest.getSession().setAttribute("sessionInfo", sessionInfo);
                    //map.put("sessionInfo", sessionInfo);
                }
                HttpHelper.setResponseJsonData(httpServletResponse, JSON.toJSONString(JsonUtil.getSuccessJsonObject(map)));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpHelper.setResponseJsonData(httpServletResponse, result);
    }
}
