package com.kk.gate.handler.Oauth;


import com.alibaba.fastjson.JSON;
import com.kk.common.util.JsonUtil;
import com.kk.common.util.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登出操作的handler
 *  使Session无效
 *  清除所有已经配置的RememberMe认证
 *  清除SecurityContextHolder
 *  跳转到/login?success
 */
public class CustomOauthLogoutSuccessHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(CustomOauthAuthenticationSuccessHandler.class);
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
      String result = JSON.toJSONString(JsonUtil.getSuccessJsonObject(true));
      logger.info("KKcms登出操作 result:{}",result);
      if (authentication != null && authentication.getDetails() != null) {
          try {
              //使当前session无效
              httpServletRequest.getSession().invalidate();
              httpServletResponse.setStatus(HttpServletResponse.SC_OK);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      HttpHelper.setResponseJsonData(httpServletResponse,result);
    }
}